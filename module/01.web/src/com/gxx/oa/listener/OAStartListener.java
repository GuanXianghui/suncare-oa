package com.gxx.oa.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * OA启动监听器
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 19:56
 */
public class OAStartListener implements ServletContextListener
{
    /**
     * 线程
     */
    private OAStartThread startThread;

    /**
     * 初始化
     * @param servletContextEvent
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (startThread == null) {
            startThread = new OAStartThread();
            startThread.start();
        }
    }

    /**
     * 结束
     * @param servletContextEvent
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (startThread != null && startThread.isInterrupted()) {
            startThread.interrupt();
        }
    }
}
