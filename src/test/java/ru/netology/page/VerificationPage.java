package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class VerificationPage {
    public static SelenideElement codeInput = $x("//span[@data-test-id='code']//input");
    public static SelenideElement verifyButton = $x("//button[@data-test-id='action-verify']");

    public VerificationPage() {
        codeInput.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void insertCode(String code) {
        codeInput.val(code);
        verifyButton.click();
    }
}
