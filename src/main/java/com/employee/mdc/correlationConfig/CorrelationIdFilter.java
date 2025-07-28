package com.employee.mdc.correlationConfig;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdFilter implements Filter {

	private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
	private static final String MDC_CORRELATION_ID_KEY = "correlationId";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);
		if (correlationId == null || correlationId.isEmpty()) {
			correlationId = UUID.randomUUID().toString(); // Generate if not present
//			 correlationId = MDC.get("traceId"); // use Sleuth-generated traceId
			 
		}

		MDC.put(MDC_CORRELATION_ID_KEY, correlationId);
		httpResponse.setHeader(CORRELATION_ID_HEADER, correlationId); // Add to response

		try {
			chain.doFilter(request, response);
		} finally {
			MDC.remove(MDC_CORRELATION_ID_KEY); // Avoid memory leak
		}
	}
}
