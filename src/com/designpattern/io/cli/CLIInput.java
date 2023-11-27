package com.designpattern.io.cli;

import com.designpattern.io.InputBoundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLIInput {
    private final Scanner scanner;
    private InputBoundary inputBoundary;

    public CLIInput(InputBoundary ib) {
        this.scanner = new Scanner(System.in);
        this.inputBoundary = ib;
    }


    public void start() {
        System.out.println("재고 관리 프로그램입니다.");
        int actionOption = 0;
            while (actionOption != 6) {
                try {
                    printOptions();
                    actionOption = Integer.parseInt(this.getUserInput());
                    parseUserActionOption(actionOption);
                } catch (NumberFormatException e) {
                    System.out.println("옵션 입력 형식은 숫자입니다.");
                }
                System.out.println();
                System.out.println("-------------------------------------------");
                System.out.println();
            }
    }

    private void parseUserActionOption(int option) {
        switch (option) {
            case 1: {
                    try {
                        System.out.println("품목의 이름을 입력해 주세요.");
                        String itemName = this.getUserInput();
                        System.out.println("품목의 가격을 입력해 주세요.");
                        int itemPrice = Integer.parseInt(this.getUserInput());

                        this.inputBoundary.addNewItem(itemName, itemPrice);
                    } catch(NumberFormatException e) {
                        System.out.println("가격은 숫자로 입력해 주세요");
                    }
                    break;
            }
            case 2: {
                    try {
                        System.out.println("상품의 이름을 입력해 주세요.(유통 기한 있음)");
                        String stockName = this.getUserInput();

                        System.out.println("상품의 개수를 입력해 주세요.");
                        int stockCount = Integer.parseInt(this.getUserInput());

                        System.out.println("상품의 유통 기한을 입력해 주세요.(yyyy-MM-dd)");
                        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate expirationDate = LocalDate.parse(this.getUserInput(), dtFormatter);

                        this.inputBoundary.addStock(stockName, stockCount, expirationDate);

                    } catch(NoSuchElementException e) {
                        System.out.println("일치하는 상품이 없습니다.");

                    } catch(NumberFormatException e) {
                        System.out.println("상품 개수는 숫자로 입력해 주세요");

                    } catch(DateTimeParseException e) {
                        System.out.println("유통 기한 입력 형식이 잘못 되었습니다.");

                    }
                    break;
            }
            case 3: {
                    try {
                        System.out.println("상품의 이름을 입력해 주세요.(유통 기한 없음)");
                        String stockName = this.getUserInput();
                        System.out.println("상품의 개수를 입력해 주세요.");
                        int stockCount = Integer.parseInt(this.getUserInput());

                        this.inputBoundary.addStock(stockName, stockCount);

                    } catch(NoSuchElementException e) {
                        System.out.println("일치하는 상품이 없습니다.");

                    } catch(NumberFormatException e) {
                        System.out.println("상품 개수는 숫자로 입력해 주세요");

                    }
                    break;
            }
            case 4: {
                    try {
                        System.out.println("상품의 이름을 입력해 주세요.");
                        String stockName = this.getUserInput();
                        System.out.println("출고할 개수를 입력해 주세요.");
                        int stockCount = Integer.parseInt(this.getUserInput());

                        this.inputBoundary.removeStock(stockName, stockCount);

                    } catch(NoSuchElementException e) {
                        System.out.println("일치하는 상품이 없습니다.");

                    } catch(NumberFormatException e) {
                        System.out.println("상품 개수는 숫자로 입력해 주세요");

                    }
                    break;
            }
            case 5: {
                    try {
                        System.out.println("상품의 이름을 입력해 주세요.");
                        String stockName = this.getUserInput();

                        this.inputBoundary.requestItemInfo(stockName);

                    } catch(NoSuchElementException e) {
                        System.out.println("일치하는 상품이 없습니다.");

                    }
                    break;
            }
            case 6: {
                System.exit(0);
            }
            default: {
                System.out.println("유효하지 않은 옵션입니다.");
                break;
            }
        }
    }

    private void printOptions() {
        System.out.println("1. 품목 추가");
        System.out.println("2. 입고(유통 기한 있음)");
        System.out.println("3. 입고(유통 기한 없음)");
        System.out.println("4. 출고");
        System.out.println("5. 품목 정보 확인");
        System.out.println("6. 프로그램 종료");
        System.out.println("원하는 옵션을 숫자로 입력해 주세요.");
    }

    private String getUserInput() {
        String userInput = this.scanner.nextLine();

        return userInput;
    }
}
