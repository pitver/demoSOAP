package ru.vershinin.demoSOAP.Controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vershinin.demoSOAP.calculate.wsdl.AddResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.DivideResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.MultiplyResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.SubtractResponse;
import ru.vershinin.demoSOAP.model.Result;
import ru.vershinin.demoSOAP.service.ResultService;


@Api(description = "Калькулятор, поодерживаемые операции: сложение, вычитание, умножение, деление")
@RestController
public class ResultController {
    final
    ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    public static final String paternFormat = "\\d+";

    @ApiOperation(value = "Сложение")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Result.class),
            @ApiResponse(code = 400, message = "Invalid input"),})
    @GetMapping("/additions")
    public Result add(
            @ApiParam(value = "Cannot empty and use only numbers.")

            @RequestParam(value = "num1", required = false, defaultValue = "1") String num1,
            // @PathVariable @NotBlank @Pattern(regexp = "\\d") int num1,
            @ApiParam(value = "Cannot empty and use only numbers.")
            @RequestParam(value = "num2", required = false, defaultValue = "1") String num2) {
        //  @PathVariable @NotBlank @Pattern(regexp = "\\d") int num2) {

        AddResponse response;
        if (num1.matches(paternFormat) && (num2.matches(paternFormat))) {
            response = resultService.add(Integer.valueOf(num1), Integer.parseInt(num2));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid input");
        }
        int res = response.getAddResult();
        return new Result(res);
    }

    @ApiOperation(value = "Вычитание")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Result.class),
            @ApiResponse(code = 400, message = "Invalid input"),})
    @GetMapping("/subtraction")
    public Result subtraction(
            @ApiParam(value = "Cannot empty and use only numbers.")

            @RequestParam(value = "num1", required = false, defaultValue = "1") String num1,
            // @PathVariable @NotBlank @Pattern(regexp = "\\d") int num1,
            @ApiParam(value = "Cannot empty and use only numbers.")
            @RequestParam(value = "num2", required = false, defaultValue = "1") String num2) {
        //  @PathVariable @NotBlank @Pattern(regexp = "\\d") int num2) {
        SubtractResponse response;
        if (num1.matches(paternFormat) && (num2.matches(paternFormat))) {
            response = resultService.subtract(Integer.valueOf(num1), Integer.parseInt(num2));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid input");
        }
        int res = response.getSubtractResult();
        return new Result(res);
    }

    @ApiOperation(value = "Умножение")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Result.class),
            @ApiResponse(code = 400, message = "Invalid input"),})
    @GetMapping("/multiply")
    public Result multiply(
            @ApiParam(value = "Cannot empty and use only numbers.")

            @RequestParam(value = "num1", required = false, defaultValue = "1") String num1,
            // @PathVariable @NotBlank @Pattern(regexp = "\\d") int num1,
            @ApiParam(value = "Cannot empty and use only numbers.")
            @RequestParam(value = "num2", required = false, defaultValue = "1") String num2) {
        //  @PathVariable @NotBlank @Pattern(regexp = "\\d") int num2) {
        MultiplyResponse response;
        if (num1.matches(paternFormat) && (num2.matches(paternFormat))) {
            response = resultService.multiply(Integer.valueOf(num1), Integer.parseInt(num2));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid input");
        }
        int res = response.getMultiplyResult();
        return new Result(res);
    }

    @ApiOperation(value = "Деление")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Result.class),
            @ApiResponse(code = 400, message = "Invalid input"),})
    @GetMapping("/division")
    public Result division(
            @ApiParam(value = "Cannot empty and use only numbers.")

            @RequestParam(value = "num1", required = false, defaultValue = "1") String num1,
            // @PathVariable @NotBlank @Pattern(regexp = "\\d") int num1,
            @ApiParam(value = "Cannot empty and use only numbers.")
            @RequestParam(value = "num2", required = false, defaultValue = "1") String num2) {
        //  @PathVariable @NotBlank @Pattern(regexp = "\\d") int num2) {
        DivideResponse response;
        if (num1.matches(paternFormat) && (num2.matches(paternFormat)) && Integer.parseInt(num2) != 0) {
            response = resultService.division(Integer.valueOf(num1), Integer.parseInt(num2));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid input");
        }
        int res = response.getDivideResult();
        return new Result(res);
    }
}
