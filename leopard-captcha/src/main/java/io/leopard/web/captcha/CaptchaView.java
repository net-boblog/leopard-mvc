package io.leopard.web.captcha;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.octo.captcha.image.ImageCaptcha;

/**
 * 验证码View.
 * 
 * @author 阿海
 * 
 */
public class CaptchaView extends ModelAndView {

	private int width;
	private int height;

	private Class<? extends CaptchaEngine> engineClazz;

	public CaptchaView() {
		this(200, 70);
	}

	public CaptchaView(int width, int height) {
		this(width, height, CaptchaEngineImpl.class);
	}

	public CaptchaView(int width, int height, Class<? extends CaptchaEngine> engineClazz) {
		super.setView(view);
		this.width = width;
		this.height = height;
		this.engineClazz = engineClazz;
	}

	private static final String SESSION_KEY = "sessCaptcha";

	private void saveSession(HttpServletRequest request, String code) {
		HttpSession session = request.getSession();
		String captchaGroupId = (String) request.getAttribute("captchaGroupId");
		String sessionKey;
		if (captchaGroupId == null || captchaGroupId.length() == 0) {
			sessionKey = SESSION_KEY;
		}
		else {
			sessionKey = SESSION_KEY + ":" + captchaGroupId;
		}
		session.setAttribute(sessionKey, code);
	}

	private AbstractUrlBasedView view = new AbstractUrlBasedView() {
		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setDateHeader("Expires", 0);
			// Set standard HTTP/1.1 no-cache headers.
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			// Set standard HTTP/1.0 no-cache header.
			response.setHeader("Pragma", "no-cache");
			// return a jpeg
			response.setContentType("image/jpeg");
			// create the image with the text

			ImageCaptcha imageCaptcha = EngineFactory.getCaptchaEngine(width, height, engineClazz).getNextImageCaptcha();
			String code = imageCaptcha.getTextChallenge();

			saveSession(request, code);
			// System.out.println("session:" + session.getId() + " code:" + code + " captchaGroupId:" + captchaGroupId + " url:" + request.getRequestURI());

			BufferedImage bi = imageCaptcha.getImageChallenge();
			// BufferedImage bi = imageCaptchaService.getImageChallengeForID(sessionId);
			ServletOutputStream out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			try {
				out.flush();
			}
			finally {
				out.close();
			}
		}
	};

}
