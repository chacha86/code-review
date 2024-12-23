package com.ll.wiseSaying;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.ll.wiseSaying.Status.*;

public class App {
    private static boolean ON_RUN = true;

    private WiseSayingController wiseSayingController;
    private CommandLineMenu cli;

    public App() {
        cli = new CommandLineMenu();
        wiseSayingController = new WiseSayingController();
    }

    public void startApp() {
        cli.printWelcomeGreeting();
        while (ON_RUN) {
            cli.printCmdInputInfo();
            try {
                run(cli.cmdInput());
            } catch (Exception e) {
                cli.printFailedInfo();
            }
        }
    }

    public void run(final String input) {
        String[] command = input.split("\\?");
        if (invalidCommand(command)) return;
        handleCommand(command);
    }

    private void handleCommand(final String[] command) {
        switch (MenuOption.findOptionByCommand(command[0])) {
            case ADD:
                runAddCommand(command);
                break;

            case LIST:
                runListCommand(command);
                break;

            case UPDATE:
                runUpdateCommand(command);
                break;

            case DELETE:
                runDeleteCommand(command);
                break;

            case BUILD:
                cli.printBuildCompleteInfo(wiseSayingController.build());
                break;

            case EXIT:
                cli.printExitInfo();
                ON_RUN = false;
                break;

            default:
                cli.printInvalidCmdInfo();
                break;
        }
    }

    private void runDeleteCommand(String[] command) {
        WiseSayingReponse response;
        if (command.length == 2) {
            response = wiseSayingController.delete(parseAttribute(command[1]));
            if (response.getStatus() == DELETE_SUCCESS) {
                cli.printDeletedInfo(response.getRecentID());
                return;
            }
            if (response.getStatus() == ID_NOT_FOUND) {
                cli.printIDNotFoundInfo(response.getRecentID());
                return;
            }
            cli.printFailedInfo();
            return;
        }
        cli.printInvalidCmdInfo();
        return;
    }

    private void runUpdateCommand(String[] command) {
        WiseSayingReponse response;
        String content;
        String author;
        if (command.length == 2) {
            try {
                WiseSaying oldWiseSaying = wiseSayingController
                        .findWiseSayingByID(Long.valueOf(parseAttribute(command[1]).get("id")))
                        .getWiseSayingList().get(0);
                cli.printOldContent(oldWiseSaying.getContent());
                content = cli.inputContent();
                cli.printOldAuthor(oldWiseSaying.getAuthor());
                author = cli.inputAuthor();
            } catch (NoSuchElementException e) {
                cli.printIDNotFoundInfo(Long.valueOf(parseAttribute(command[1]).get("id")));
                return;
            } catch (Exception e) {
                cli.printFailedInfo();
                return;
            }
            response = wiseSayingController.update(parseAttribute(command[1])
                    .add("content", content)
                    .add("author", author)
            );
            if (response.getStatus() == ID_NOT_FOUND) {
                cli.printIDNotFoundInfo(response.getRecentID());
                return;
            }
            if (response.getStatus() == UPDATE_SUCCESS) {
                return;
            }
            cli.printFailedInfo();
            return;
        }
        cli.printInvalidCmdInfo();
    }

    private void runListCommand(String[] command) {
        WiseSayingReponse response;
        if (command.length == 1) {
            response = wiseSayingController.list();
            if (response.getStatus() == LIST_SUCCESS) {
                cli.printWiseSayingList(
                        response.getWiseSayingList(),
                        response.getCurrentPage(),
                        response.getTotalPage()
                );
                return;
            }
            cli.printFailedInfo();
            return;
        }
        if (command.length == 2) {
            response = wiseSayingController.list(parseAttribute(command[1]));
            if (response.getStatus() == LIST_SUCCESS) {
                cli.printWiseSayingList(
                        response.getWiseSayingList(),
                        response.getCurrentPage(),
                        response.getTotalPage()
                );
                return;
            }
            cli.printFailedInfo();
            return;
        }
        cli.printInvalidCmdInfo();
    }

    private void runAddCommand(String[] command) {
        WiseSayingReponse response;
        String author;
        String content;
        if (command.length == 1) {
            try {
                content = cli.inputContent();
                author = cli.inputAuthor();
            } catch (Exception e) {
                cli.printFailedInfo();
                return;
            }
            response = wiseSayingController.add(content, author);
            if (response.getStatus() == ADD_SUCCESS) {
                cli.printAdddedInfo(response.getRecentID());
                return;
            }
            cli.printFailedInfo();
            return;
        }
        cli.printInvalidCmdInfo();
    }

    private Attribute parseAttribute(final String command) {
        Attribute attribute = new Attribute();
        Arrays.stream(command.split("&"))
                .map(param -> param.split("=", 2))
                .filter(parts -> parts.length == 2)
                .forEach(parts -> attribute.add(parts[0], parts[1]));
        return attribute;
    }

    private boolean invalidCommand(final String[] command) {
        if (!Arrays.stream(MenuOption.values())
                .anyMatch(option -> option.getCommand().equals(command[0]))) {
            cli.printInvalidCmdInfo();
            return true;
        }
        return false;
    }
}
