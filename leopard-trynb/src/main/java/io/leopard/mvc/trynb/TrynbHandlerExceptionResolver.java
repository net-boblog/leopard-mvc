package io.leopard.mvc.trynb;

import io.leopard.mvc.trynb.model.TrynbInfo;
import io.leopard.mvc.trynb.resolver.TrynbResolver;
import io.leopard.mvc.trynb.resolver.TrynbResolverImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class TrynbHandlerExceptionResolver implements HandlerExceptionResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	private TrynbService errorPageService = new TrynbServiceImpl();
	private TrynbResolver trynbResolver = new TrynbResolverImpl();

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		// System.err.println("resolveException:" + request);
		if (!(handler instanceof HandlerMethod)) {
			return null;
		}
		String uri = request.getRequestURI();

		TrynbInfo trynbInfo = errorPageService.parse(request, uri, exception);
		return trynbResolver.resolveView(request, response, ((HandlerMethod) handler), exception, trynbInfo);
	}

}
