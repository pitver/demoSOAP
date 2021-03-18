package ru.vershinin.demoSOAP.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.vershinin.demoSOAP.calculate.wsdl.*;

public class SOAPConnector extends WebServiceGatewaySupport {


    public AddResponse additions(int a, int b) {

        Add request = new Add();
        request.setIntA(a);
        request.setIntB(b);
        System.out.println();
        System.out.println("Requesting forecast for " + a + " + " + b);
        AddResponse response = (AddResponse) getWebServiceTemplate().marshalSendAndReceive(
                request,
                new SoapActionCallback(
                        "http://tempuri.org/Add"));
        return response;
    }

    public SubtractResponse subtraction(int a, int b) {
        Subtract request = new Subtract();
        request.setIntA(a);
        request.setIntB(b);
        System.out.println();
        System.out.println("Requesting forecast for " + a + " - " + b);
        SubtractResponse response = (SubtractResponse) getWebServiceTemplate().marshalSendAndReceive(
                request,
                new SoapActionCallback(
                        "http://tempuri.org/Subtract"));
        return response;
    }

    public DivideResponse division(int a, int b) {
        Divide request = new Divide();
        request.setIntA(a);
        request.setIntB(b);
        System.out.println();
        System.out.println("Requesting forecast for " + a + " / " + b);
        DivideResponse response = (DivideResponse) getWebServiceTemplate().marshalSendAndReceive(
                request,
                new SoapActionCallback(
                        "http://tempuri.org/Divide"));
        return response;
    }
    public MultiplyResponse multiplication(int a, int b) {
        Multiply request = new Multiply();
        request.setIntA(a);
        if(b==0){
            throw new IllegalArgumentException();
        }
        request.setIntB(b);
        System.out.println();
        System.out.println("Requesting forecast for " + a + " * " + b);
        MultiplyResponse response = (MultiplyResponse) getWebServiceTemplate().marshalSendAndReceive(
                request,
                new SoapActionCallback(
                        "http://tempuri.org/Multiply"));
        return response;
    }


}