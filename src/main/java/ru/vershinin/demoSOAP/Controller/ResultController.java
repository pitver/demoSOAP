package ru.vershinin.demoSOAP.Controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vershinin.demoSOAP.calculate.wsdl.AddResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.DivideResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.MultiplyResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.SubtractResponse;
/*import ru.vershinin.demoSOAP.client.Receiver;*/
import ru.vershinin.demoSOAP.client.Sender;
import ru.vershinin.demoSOAP.model.Result;
import ru.vershinin.demoSOAP.service.ResultService;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Random;
import java.util.UUID;


@Api(description = "Калькулятор, поодерживаемые операции: сложение, вычитание, умножение, деление")
@RestController
public class ResultController {

    private final Sender sender;

/*    private final Receiver receiver;*/
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination statusDestination;


    final
    ResultService resultService;

    public ResultController(ResultService resultService, Sender sender) {
        this.resultService = resultService;
        this.sender = sender;

    }

    public static final String paternFormat = "\\d+";

    @ApiOperation(value = "Сложение")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Result.class),
            @ApiResponse(code = 400, message = "Invalid input"),})

    @GetMapping("/additions")
    public String add(
            @ApiParam(value = "Cannot empty and use only numbers.")

            @RequestParam(value = "num1", required = false, defaultValue = "1") String num1,

            // @PathVariable @NotBlank @Pattern(regexp = "\\d") int num1,
            @ApiParam(value = "Cannot empty and use only numbers.")
            @RequestParam(value = "num2", required = false, defaultValue = "1") String num2) throws JMSException {
        //  @PathVariable @NotBlank @Pattern(regexp = "\\d") int num2) {

        AddResponse response;
        if (num1.matches(paternFormat) && (num2.matches(paternFormat))) {

            response = resultService.add(Integer.parseInt(num1), Integer.parseInt(num2));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid input");
        }
        int res = response.getAddResult();

        return getCorrelationId(res);
    }

    @GetMapping("/getres")
    public String getResult(

            @RequestParam(value = "correlationId", required = false, defaultValue = "1") String correlationId) {

        String status = sender.receiveOrderStatus(correlationId);

        return status;
    }


    @ApiOperation(value = "Вычитание")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Result.class),
            @ApiResponse(code = 400, message = "Invalid input"),})
    @GetMapping("/subtraction")
    public String subtraction(
            @ApiParam(value = "Cannot empty and use only numbers.")

            @RequestParam(value = "num1", required = false, defaultValue = "1") String num1,
            // @PathVariable @NotBlank @Pattern(regexp = "\\d") int num1,
            @ApiParam(value = "Cannot empty and use only numbers.")
            @RequestParam(value = "num2", required = false, defaultValue = "1") String num2) throws JMSException {
        //  @PathVariable @NotBlank @Pattern(regexp = "\\d") int num2) {
        SubtractResponse response;
        if (num1.matches(paternFormat) && (num2.matches(paternFormat))) {
            response = resultService.subtract(Integer.parseInt(num1), Integer.parseInt(num2));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid input");
        }
        int res = response.getSubtractResult();
        return getCorrelationId(res);

    }

    @ApiOperation(value = "Умножение")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Result.class),
            @ApiResponse(code = 400, message = "Invalid input"),})
    @GetMapping("/multiply")
    public String multiply(
            @ApiParam(value = "Cannot empty and use only numbers.")

            @RequestParam(value = "num1", required = false, defaultValue = "1") String num1,
            // @PathVariable @NotBlank @Pattern(regexp = "\\d") int num1,
            @ApiParam(value = "Cannot empty and use only numbers.")
            @RequestParam(value = "num2", required = false, defaultValue = "1") String num2) throws JMSException {
        //  @PathVariable @NotBlank @Pattern(regexp = "\\d") int num2) {
        MultiplyResponse response;
        if (num1.matches(paternFormat) && (num2.matches(paternFormat))) {
            response = resultService.multiply(Integer.parseInt(num1), Integer.parseInt(num2));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid input");
        }
        int res = response.getMultiplyResult();
        return getCorrelationId(res);
    }

    @ApiOperation(value = "Деление")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Result.class),
            @ApiResponse(code = 400, message = "Invalid input"),})
    @GetMapping("/division")
    public String division(
            @ApiParam(value = "Cannot empty and use only numbers.")

            @RequestParam(value = "num1", required = false, defaultValue = "1") String num1,
            // @PathVariable @NotBlank @Pattern(regexp = "\\d") int num1,
            @ApiParam(value = "Cannot empty and use only numbers.")
            @RequestParam(value = "num2", required = false, defaultValue = "1") String num2) throws JMSException {
        //  @PathVariable @NotBlank @Pattern(regexp = "\\d") int num2) {
        DivideResponse response;
        if (num1.matches(paternFormat) && (num2.matches(paternFormat)) && Integer.parseInt(num2) != 0) {
            response = resultService.division(Integer.parseInt(num1), Integer.parseInt(num2));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid input");
        }
        int res = response.getDivideResult();
        return getCorrelationId(res);
    }
    /*@JmsListener(destination = "${destination.order}")*/
    private String getCorrelationId(int res) throws JMSException {
        String correlationId = sender.sendOrder(String.valueOf(new Random().nextInt(1000000)));


        System.out.println(correlationId);

        jmsTemplate.send(statusDestination, (Session messageCreator) -> {
            TextMessage message =
                    messageCreator.createTextMessage(String.valueOf(res));
            message.setJMSCorrelationID(correlationId);
            return message;
        });
        return correlationId;
    }
}
