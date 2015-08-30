package io.leopard.web.captcha.controller;

import io.leopard.httpnb.Httpnb;
import io.leopard.jetty.JettyServer;
import io.leopard.web.captcha.CaptchaView;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CaptchaController {

	@RequestMapping("/captcha.do")
	public CaptchaView captcha(Long sessUid) {
		return new CaptchaView();
	}

	@Test
	public void testCaptcha() throws Exception {
		JettyServer.start("src/test/webapp");
		{
			Httpnb.doGet("http://localhost/passport/login.leo?uid=1");
		}
		String result = Httpnb.doGet("http://localhost/welcome.do");
		System.out.println("result:" + result);
		Assert.assertEquals("{\"status\":\"RuntimeException\",\"message\":\"ok\",\"data\":null}", result);
	}

	public static void main(String[] args) throws Exception {
		JettyServer.start("src/test/webapp");
	}
}