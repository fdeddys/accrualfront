package com.ddabadi.Report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by deddy on 5/21/16.
 */

@RestController
public class ReportController {


    public void previewReport(String fileReport,Map parameter, List dataSource, String judul, HttpServletResponse response){


        try {
            JRDataSource jrDataSource = new JRBeanArrayDataSource(dataSource.toArray());
            InputStream jasperStream = ReportController.class.getResourceAsStream(fileReport);

            JasperReport jasperReport=null;

            jasperReport =  (JasperReport )JRLoader.loadObject(jasperStream);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,parameter,jrDataSource);
            response.setContentType("application/pdf");
            final OutputStream outputStream= response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
