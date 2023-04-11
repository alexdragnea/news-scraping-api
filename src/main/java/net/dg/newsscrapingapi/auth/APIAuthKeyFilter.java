package net.dg.newsscrapingapi.auth;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@AllArgsConstructor
public class APIAuthKeyFilter extends AbstractPreAuthenticatedProcessingFilter {
  private String principalRequestHeader;

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getHeader(principalRequestHeader);
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "N/A";
  }
}
