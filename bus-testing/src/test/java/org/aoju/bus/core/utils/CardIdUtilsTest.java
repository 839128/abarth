package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Test;

public class CardIdUtilsTest {

    @Test
    public void isValidCardTest() {
        String card1 = "6228480402564890018";
        String card2 = "6228480402637874213";
        String card3 = "6228481552887309119";
        String card4 = "6228480801416266113";
        String card5 = "6228481698729890079";
        String card6 = "6228482316158625861";
        String card7 = "621661280000447287";

        System.out.println("银行卡号：" + card1 + "  check code: " + CardUtils.getBankCardCode(card1) + " 是否为银行卡: " + CardUtils.isBankCard(card1));
        System.out.println("银行卡号：" + card2 + "  check code: " + CardUtils.getBankCardCode(card2) + " 是否为银行卡: " + CardUtils.isBankCard(card2));
        System.out.println("银行卡号：" + card3 + "  check code: " + CardUtils.getBankCardCode(card3) + " 是否为银行卡: " + CardUtils.isBankCard(card3));
        System.out.println("银行卡号：" + card4 + "  check code: " + CardUtils.getBankCardCode(card4) + " 是否为银行卡: " + CardUtils.isBankCard(card4));
        System.out.println("银行卡号：" + card5 + "  check code: " + CardUtils.getBankCardCode(card5) + " 是否为银行卡: " + CardUtils.isBankCard(card5));
        System.out.println("银行卡号：" + card6 + "  check code: " + CardUtils.getBankCardCode(card6) + " 是否为银行卡: " + CardUtils.isBankCard(card6));
        System.out.println("银行卡号：" + card7 + "  check code: " + CardUtils.getBankCardCode(card7) + " 是否为银行卡: " + CardUtils.isBankCard(card7));

        System.out.println("银行卡号：" + card6 + "  check code: " + CardUtils.getNameOfBank(card6));
    }

}
