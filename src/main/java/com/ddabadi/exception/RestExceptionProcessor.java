package com.ddabadi.exception;

import com.ddabadi.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;


/**
 * Created by deddy on 5/10/16.
 */

@ControllerAdvice
public class RestExceptionProcessor {

    private Logger logger = Logger.getLogger("Rest Exception");

    @ExceptionHandler(BagianNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo BagianNotFound(HttpServletRequest req, BagianNotFoundException ex) {
        String errorMessage = "Bagian tidak ada -> ";

        errorMessage += ex.getBagianId();
        String errorURL = req.getRequestURL().toString();
        logger.info("Error from " + req.getRequestURL().toString());
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(InvalidKarakterException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo invalidKarakterErr(HttpServletRequest req, InvalidKarakterException ex) {
        String errorMessage = "Karakter tidak valid -> ";

        errorMessage += ex.getKarakter();
        String errorURL = req.getRequestURL().toString();

        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(VoucherBelumPostingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo belumPostingErr(HttpServletRequest req, VoucherBelumPostingException ex){
        String errUrl = req.getRequestURL().toString();
        return new ErrorInfo(errUrl,"Ada Voucher belum posting");
    }

    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo tglError(HttpServletRequest req, InvalidDateException ex){
        String errUrl = req.getRequestURL().toString();
        return new ErrorInfo(errUrl,"Tangal tidak valid");
    }


}
