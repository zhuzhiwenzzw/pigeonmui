package top.zhuzhiwen.pigeonmui.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author CoolWen
 * @version 2018-10-31 10:31
 */
//@Component
public class ResourceCheckFilter extends AccessControlFilter {

    protected static final Logger logger = LoggerFactory.getLogger(ResourceCheckFilter.class);


    private String errorUrl;


    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        logger.debug("-----------------ResourceCheckFilter开始");
//        Subject subject = SecurityUtils.getSubject();
        Subject subject = getSubject(request, response);
        logger.debug("过滤器之subject" + subject);
        String url = getPathWithinApplication(request);
        logger.debug("过滤器之url:" + url);
        return subject.isPermitted(url);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse hsp = (HttpServletResponse) response;
        HttpServletRequest hReq = (HttpServletRequest) request;
        logger.debug(hReq.getContextPath() + this.errorUrl);
        hsp.sendRedirect(hReq.getContextPath() + "/" + this.errorUrl);
        //       logger.info("过滤器之ResourceCheckFilter:" + hReq.getContextPath());
        //       logger.info("过滤器之errorUrl:" + errorUrl);
        logger.debug("ResourceCheckFilter-----------------权限拒绝");
        return false;

    }
}
