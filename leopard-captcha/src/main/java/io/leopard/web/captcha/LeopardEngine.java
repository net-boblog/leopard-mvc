package io.leopard.web.captcha;

import com.octo.captcha.image.ImageCaptcha;

public interface LeopardEngine {
	void initialFactories();

	void setWidth(int width);

	void setHeight(int height);

	ImageCaptcha getNextImageCaptcha();
}
