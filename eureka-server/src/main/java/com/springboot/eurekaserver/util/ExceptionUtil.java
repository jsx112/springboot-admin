package com.springboot.eurekaserver.util;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
* 异常的工具类.
* @author jiasx
* @create 2018/2/5 16:55
**/
public class ExceptionUtil {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		if (e == null) {
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	@SafeVarargs
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}

	/**
	 * 在request中获取异常类
	 * 
	 * @param request
	 * @return
	 */
	public static Throwable getThrowable(HttpServletRequest request) {
		Throwable ex = null;
		if (request.getAttribute("exception") != null) {
			ex = (Throwable) request.getAttribute("exception");
		} else if (request.getAttribute("javax.servlet.error.exception") != null) {
			ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}
		return ex;
	}

	/**
	 * 获取异常信息的字符串描述
	 *
	 * @param e
	 * @return
	 */
	public static String getExceptionStr(Exception e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		String s = stringWriter.toString();
		String separator = "\r\n\t";
		if (!isWinOs()) {
			separator = "\n\t";
		}
		int i = s.indexOf(separator);
		StringBuffer errCauseBuf = new StringBuffer("错误：").append(s.substring(0, i)).append(", ").append(s.substring(i + 3, s.indexOf(separator, i + 6)));
		int j;
		String cause = "Caused by:";
		if ((j = s.lastIndexOf(cause)) != -1) {
			errCauseBuf.append("；").append(s.substring(j, s.indexOf(separator, j + 10)));
		}

		return errCauseBuf.toString();
	}



	/**
	 * 判断操作系统是win还是linux
	 *
	 * @return
	 */
	public static boolean isWinOs() {
		boolean isWinOs = false;
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			isWinOs = true;
		}
		return isWinOs;
	}

}
