package com.designpattern.io.cli;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.exception.OutOfStockException;
import com.designpattern.io.InputBoundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CLIInput {
    private final Scanner scanner;
    private final InputBoundary inputBoundary;

    public CLIInput(InputBoundary ib) {
        this.scanner = new Scanner(System.in);
        this.inputBoundary = ib;
    }


    public void start() {
        System.out.println("재고 관리 프로그램입니다.");
        InputOption actionOption = InputOption.SELL_STOCK;
            while (actionOption != InputOption.EXIT) {
                try {
                    printOptions();
                    actionOption = InputOption.values()[Integer.parseInt(this.getUserInput())-1];
                    parseUserActionOption(actionOption);
                } catch (NumberFormatException e) {
                    System.out.println("옵션 내의 숫자를 입력해 주세요.");
                }
                System.out.println();
                System.out.println("-------------------------------------------");
                System.out.println();
            }
    }

    private void parseUserActionOption(InputOption option) {
        switch (option) {
            case SELL_STOCK: {
                    try {
                        System.out.println("판매할 물건의 이름을 입력해 주세요(취소: c).");
                        String stockName = this.getUserInput();
                        if (stockName.equals("c")) break;
                        System.out.println("판매할 개수를 입력해 주세요(취소: c).");
                        String stockCountInput = this.getUserInput();
                        if (stockCountInput.equals("c")) break;
                        int stockCount = Integer.parseInt(stockCountInput);

                        this.inputBoundary.sellStocks(stockName, stockCount);

                        System.out.println("정상적으로 판매되었습니다.");

                    } catch(NoSuchProductException e) {
                        System.out.println("일치하는 상품이 없습니다.");
                        parseUserActionOption(option);

                    } catch(NumberFormatException e) {
                        System.out.println("상품 개수는 숫자로 입력해 주세요");
                        parseUserActionOption(option);
                    } catch(OutOfStockException e) {
                        System.out.println("판매할 상품의 개수가 부족합니다.");
                        parseUserActionOption(option);
                    }
                    break;
            }
            case ADD_STOCK: {
                    try {
                        System.out.println("입고할 물건의 이름을 입력해 주세요(취소: c).");
                        String stockName = this.getUserInput();
                        if (stockName.equals("c")) break;

                        System.out.println("입고할 개수를 입력해 주세요(취소: c).");
                        String stockCountInput = this.getUserInput();
                        if (stockCountInput.equals("c")) break;
                        int stockCount = Integer.parseInt(stockCountInput);

                        System.out.println("상품의 유통 기한을 입력해 주세요(yyyy-MM-dd). 유통 기한이 없다면 바로 [Enter]를 눌러 주세요(취소: c).");
                        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String expirationDateInput = this.getUserInput();
                        if (expirationDateInput.equals("c")) break;

                        if (expirationDateInput.isEmpty()) {
                            this.inputBoundary.addStock(stockName, stockCount);
                            System.out.println("정상적으로 재입고되었습니다.");
                            break;
                        }
                        this.inputBoundary.addStock(stockName, stockCount, LocalDate.parse(expirationDateInput, dtFormatter));
                        System.out.println("정상적으로 재입고되었습니다.");
                    } catch(NoSuchProductException e) {
                        System.out.println("일치하는 상품이 없습니다.");
                        parseUserActionOption(option);

                    } catch(NumberFormatException e) {
                        System.out.println("상품 개수는 숫자로 입력해 주세요");
                        parseUserActionOption(option);

                    } catch(DateTimeParseException e) {
                        System.out.println("유통 기한 입력 형식이 잘못 되었습니다.");
                        parseUserActionOption(option);

                    }
                    break;
            }
            case ADD_PRODUCT: {
                    try {
                        System.out.println("품목의 이름을 입력해 주세요(취소: c).");
                        String productName = this.getUserInput();
                        if (productName.equals("c")) break;

                        System.out.println("품목의 가격을 입력해 주세요(취소: c).");
                        String productPriceInput = this.getUserInput();
                        if (productPriceInput.equals("c")) break;
                        int productPrice = Integer.parseInt(productPriceInput);

                        this.inputBoundary.addNewProduct(productName, productPrice);

                        System.out.println("정상적으로 등록되었습니다.");
                    } catch(NumberFormatException e) {
                        System.out.println("가격은 숫자로 입력해 주세요");
                        parseUserActionOption(option);
                    } catch(NoSuchProductException e) {
                        System.out.println("일치하는 상품이 없습니다.");
                        parseUserActionOption(option);
                    }
                    break;
            }
            case REMOVE_PRODUCT: {
                    try {
                        System.out.println("품목의 이름을 입력해 주세요(취소: c).");
                        String productName = this.getUserInput();
                        if (productName.equals("c")) break;

                        this.inputBoundary.deleteProduct(productName);

                        System.out.println("정상적으로 삭제되었습니다.");
                    } catch(NoSuchProductException e) {
                        System.out.println("일치하는 상품이 없습니다.");
                        parseUserActionOption(option);
                    }
                    break;
            }
            case EXIT: {
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            }
            default: {
                System.out.println("유효하지 않은 옵션입니다.");
                break;
            }
        }
    }

    private void printOptions() {
        System.out.println("1. 물건 판매");
        System.out.println("2. 물건 재입고");
        System.out.println("3. 품목 등록");
        System.out.println("4. 품목 삭제");
        System.out.println("5. 프로그램 종료");
        System.out.println("원하는 옵션을 숫자로 입력해 주세요.");
    }

    private String getUserInput() {
        return this.scanner.nextLine().trim();
    }
}
