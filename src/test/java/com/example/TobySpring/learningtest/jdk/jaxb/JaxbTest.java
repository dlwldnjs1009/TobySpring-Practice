//package com.example.TobySpring.learningtest.jdk.jaxb;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.example.TobySpring.user.sqlservice.jaxb.SqlType;
//import com.example.TobySpring.user.sqlservice.jaxb.Sqlmap;
//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.JAXBException;
//import jakarta.xml.bind.Unmarshaller;
//import java.io.IOException;
//import java.util.List;
//import javax.xml.XMLConstants;
//import javax.xml.validation.Schema;
//import javax.xml.validation.SchemaFactory;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.xml.sax.SAXException;
//
//public class JaxbTest {
//    @Test
//    public void readSqlMap() throws JAXBException, IOException {
//        // 네임스페이스 포함된 contextPath
//        String contextPath = Sqlmap.class.getPackage().getName();
//        JAXBContext context = JAXBContext.newInstance(contextPath);
//
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//
//        // 네임스페이스 포함된 xml 파일을 읽어옴
//        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(getClass().getResourceAsStream("file:/Users/jiwon/Documents/coding/TobySpring/src/test/resources/sqlmap.xml"));
//
//        List<SqlType> sqlList = sqlmap.getSql();
//
//        assertThat(sqlList.size()).isEqualTo(3);
//        assertThat(sqlList.get(0).getKey()).isEqualTo("add");
//        assertThat(sqlList.get(0).getValue()).isEqualTo("insert");
//        assertThat(sqlList.get(1).getKey()).isEqualTo("get");
//        assertThat(sqlList.get(1).getValue()).isEqualTo("select");
//        assertThat(sqlList.get(2).getKey()).isEqualTo("delete");
//        assertThat(sqlList.get(2).getValue()).isEqualTo("delete");
//    }
//
//}
