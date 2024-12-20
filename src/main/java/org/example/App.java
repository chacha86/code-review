package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    private final WiseSayingController controller;
    private final Scanner scanner;

    private enum COMMAND{
        EXIT("종료"),
        CREATE("등록"),
        LIST("목록"),
        DELETE("삭제"),
        UPDATE("수정"),
        BUILD("빌드")
        ;
        String name;
        COMMAND(String name) {
            this.name = name;
        }

        static COMMAND of(String name){
            switch (name){
                case "등록" -> {
                    return COMMAND.CREATE;
                }
                case "종료" -> {
                    return COMMAND.EXIT;
                }
                case "목록" -> {
                    return COMMAND.LIST;
                }
                case "수정" -> {
                    return COMMAND.UPDATE;
                }
                case "빌드" -> {
                    return COMMAND.BUILD;
                }
                case "삭제" -> {
                    return COMMAND.DELETE;
                }
                default -> throw new IllegalArgumentException("Unknown command: " + name);
            }
        }
    }
    
    public enum PARAMETERS{
        KEYWORD_TYPE("keywordType"),KEYWORD("keyword"),PAGE_NUM("page"),
        ID("id"), OPERATOR_SPLITTER("\\?"),PARAMETER_SPLITTER("&"), PARAMETER_VALUE_SPLITTER("="),;
        String value;
        PARAMETERS(String value) {
            this.value = value;
        }
    }

    public App(WiseSayingController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    /**
     * 사용자로부터 명령 입력을 받아 세부 로직으로 라우팅함.
     */
    public void run(){
        System.out.println("== 명언 앱 == ");
        while(true){
            System.out.print("명령) ");
            // 임시방편 처리. 스캐너의 입력이 비어있는 채로 nextLine을 만나면 오류를 뱉어서
            // 입력 버퍼가 비었으면 출력 버퍼를 다 비우고 종료시킴
            if(!scanner.hasNextLine()){
                System.out.flush();
                return;
            }
            String input = scanner.nextLine();

            Map<String, String> parsedParameterMap = getParsedParameterMap(input);
            COMMAND operator = COMMAND.of(parsedParameterMap.get("operator"));

            try{
                route(operator, parsedParameterMap, input);
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void route(COMMAND operator, Map<String, String> parsedParameterMap, String input) {
        switch (operator) {
            case EXIT -> controller.exit();
            case CREATE -> controller.create();
            case LIST -> {
                if(parsedParameterMap.containsKey("keywordType") && parsedParameterMap.containsKey("keyword"))
                    controller.search(parsedParameterMap.get("keywordType"),
                            parsedParameterMap.get("keyword"),
                            Integer.parseInt(parsedParameterMap.get("page")));
                else
                    controller.readAll(Integer.parseInt(parsedParameterMap.get("page")));
            }
            case DELETE -> controller.delete(Integer.parseInt(parsedParameterMap.get(PARAMETERS.ID.value)));
            case UPDATE -> controller.update(Integer.parseInt(parsedParameterMap.get(PARAMETERS.ID.value)));
            case BUILD -> controller.build();
        }
    }

    private static Map<String, String> getParsedParameterMap(String input){
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(PARAMETERS.PAGE_NUM.value, "1");


        String[] split = input.split(PARAMETERS.OPERATOR_SPLITTER.value);
        String operator = split[0];
        parameterMap.put("operator",operator);

        String parameter = split.length == 2?split[1]:"";

        if(parameter.isEmpty())
            return parameterMap;
        String[] parsedParams = parameter.split(PARAMETERS.PARAMETER_SPLITTER.value);

        for(String param : parsedParams)
            parameterMap.put(param.split(
                    PARAMETERS.PARAMETER_VALUE_SPLITTER.value)[0],
                    param.split(PARAMETERS.PARAMETER_VALUE_SPLITTER.value)[1]);

        return parameterMap;
    }
}
