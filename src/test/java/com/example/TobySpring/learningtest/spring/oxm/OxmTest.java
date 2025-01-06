package com.example.TobySpring.learningtest.spring.oxm;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.TobySpring.user.sqlservice.jaxb.SqlType;
import com.example.TobySpring.user.sqlservice.jaxb.Sqlmap;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:OxmTest-context.xml")
public class OxmTest {
//    @Autowired
//    Unmarshaller unmarshaller;
//
//    @Test
//    public void unmarshallSqlMap() throws XmlMappingException, IOException, JAXBException {
//        Source xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
//
//        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);
//
//        List<SqlType> sqlList = sqlmap.getSql();
//        assertThat(sqlList.size()).isEqualTo(3);
//        assertThat(sqlList.get(0).getKey()).isEqualTo("add");
//        assertThat(sqlList.get(0).getValue()).isEqualTo("insert");
//        assertThat(sqlList.get(1).getKey()).isEqualTo("get");
//        assertThat(sqlList.get(1).getValue()).isEqualTo("select");
//        assertThat(sqlList.get(2).getKey()).isEqualTo("delete");
//        assertThat(sqlList.get(2).getValue()).isEqualTo("delete");
//    }
}
