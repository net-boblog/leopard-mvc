package io.leopard.web.captcha.controller;

import io.leopard.jetty.JettyServer;
import io.leopard.web.captcha.CaptchaGroup;
import io.leopard.web.captcha.CaptchaView;
import io.leopard.web.view.JsonView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CaptchaController {

	@RequestMapping("/captcha.do")
	public CaptchaView captcha() {
		return new CaptchaView();
	}

	@RequestMapping("/check.do")
	public JsonView check(String sessCaptcha) {
		return new JsonView("sessCaptcha:" + sessCaptcha);
	}

	@RequestMapping("/captcha2.do")
	public CaptchaView captcha2() {
		return new CaptchaView("captcha2");
	}

	@RequestMapping("/check2.do")
	@CaptchaGroup("captcha2")
	public JsonView check2(String sessCaptcha) {
		return new JsonView("sessCaptcha:" + sessCaptcha);
	}

	// @RequestMapping("/check3.do")
	// public JsonView check3(String sessCaptcha, @CaptchaGroup("captcha2") String captcha2) {
	// return new JsonView("sessCaptcha:" + sessCaptcha + " captcha2:" + captcha2);
	// }

	public static void main(String[] args) throws Exception {
		JettyServer.start("src/test/webapp");
	}
}
