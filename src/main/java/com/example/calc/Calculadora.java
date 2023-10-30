package com.example.calc;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculadora {
    public Calculadora() {
    }

    public boolean verificar(String operacion) {
        String pattern = "[a-zA-Z]";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(operacion);

        if (matcher.find()) {
            return false;
        }
        return true;
    }

    public Double evaluarExpresion(String expresion) {
        try {
            Stack<Double> numeros = new Stack<>();
            Stack<Character> operadores = new Stack<>();
            for (int i = 0; i < expresion.length(); i++) {
                char caracter = expresion.charAt(i);
                if (Character.isDigit(caracter)) {
                    // Verificar si el número es decimal
                    StringBuilder num = new StringBuilder();
                    num.append(caracter);
                    int j = i + 1;
                    while (j < expresion.length() && (Character.isDigit(expresion.charAt(j)) || expresion.charAt(j) == '.')) {
                        num.append(expresion.charAt(j));
                        j++;
                    }
                    i = j - 1;
                    double numero = Double.parseDouble(num.toString());
                    numeros.push(numero);
                } else if (Character.isDigit(caracter) ) {
                    double numero = Character.getNumericValue(caracter);
                    numeros.push(numero);
                } else if (caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/') {
                    while (numeros.size() >= 2 && !operadores.isEmpty() && precedencia(operadores.peek()) >= precedencia(caracter)) {
                        realizarOperacion(numeros, operadores);
                    }
                    operadores.push(caracter);
                } else if (caracter == '(') {
                    operadores.push(caracter);
                } else if (caracter == ')') { //
                    while (!operadores.isEmpty() && operadores.peek() != '(') {
                        realizarOperacion(numeros, operadores);
                    }
                    if (operadores.isEmpty() || operadores.pop() != '(') {
                        throw new IllegalArgumentException("Error: paréntesis desequilibrados");
                    }
                }
            }

            while (!operadores.isEmpty()) {
                realizarOperacion(numeros, operadores);
            }
            return numeros.pop();

        } catch (Exception e) {
            throw new RuntimeException("Syntaz error");
        }
    }

    private int precedencia(char operador) {
        if (operador == '+' || operador == '-') {
            return 1;
        } else if (operador == '*' || operador == '/') {
            return 2;
        }
        return 0;
    }

    private void realizarOperacion(Stack<Double> numeros, Stack<Character> operadores) {
        Double b = numeros.pop();
        Double a = numeros.pop();
        char operador = operadores.pop();
        Double resultado = 0d;

        switch (operador) {
            case '+':
                resultado = a + b;
                break;
            case '-':
                resultado = a - b;
                break;
            case '*':
                resultado = a * b;
                break;
            case '/':
                resultado = a / b;
                break;
        }

        numeros.push(resultado);
    }

    public void operar(String operacion) {
        try{
            if (verificar(operacion)) {
                Double resultado = evaluarExpresion(operacion);
                System.out.println("Resultado: " + resultado);
            } else {
                System.out.println("Caracter no válido");
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}