package com.felipelalmeida.services;

import com.felipelalmeida.utils.NumberConverter;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    public Double sum(String numberOne, String numberTwo) {
        return NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo);
    }

    public Double subtraction(String numberOne, String numberTwo) {
        return NumberConverter.convertToDouble(numberOne) - NumberConverter.convertToDouble(numberTwo);
    }

    public Double multiplication(String numberOne, String numberTwo) {
        return NumberConverter.convertToDouble(numberOne) * NumberConverter.convertToDouble(numberTwo);
    }

    public Double division(String numberOne, String numberTwo) {
        return NumberConverter.convertToDouble(numberOne) / NumberConverter.convertToDouble(numberTwo);
    }

    public Double media(String numberOne, String numberTwo) {
        return (NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo)) / 2;
    }

    public Double sqrt(String number) {
        return Math.sqrt(NumberConverter.convertToDouble(number));
    }

}
