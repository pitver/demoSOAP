package ru.vershinin.demoSOAP;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.vershinin.demoSOAP.calculate.wsdl.AddResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.DivideResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.MultiplyResponse;
import ru.vershinin.demoSOAP.calculate.wsdl.SubtractResponse;
import ru.vershinin.demoSOAP.config.SoapWebServiceConfiguration;
import ru.vershinin.demoSOAP.client.SOAPConnector;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SoapWebServiceConfiguration.class, loader = AnnotationConfigContextLoader.class)
class DemoSoapApplicationTests {

    @Autowired
    SOAPConnector soapConnector;

    @Test
    void testAdd() {
        AddResponse addResponse = soapConnector.additions(3, 4);
        assertEquals(7, addResponse.getAddResult());
    }

    @Test
    void testSubtract() {
        SubtractResponse subtractResponse = soapConnector.subtraction(5, 2);
        assertEquals(3, subtractResponse.getSubtractResult());
    }

    @Test
    void testDivide() {
        DivideResponse divideResponse = soapConnector.division(8, 4);
        assertEquals(2, divideResponse.getDivideResult());
    }

    /*@Test
    void testDiviveByZero() {
        DivideResponse divideResponse = soapConnector.division(8, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            soapConnector.division(8,0);
        });

    }*/

    @Test
    void testMultiply() {
        MultiplyResponse multiplyResponse = soapConnector.multiplication(2, 3);
        assertEquals(6, multiplyResponse.getMultiplyResult());
    }

}
