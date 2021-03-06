package com.gs.controller;

import com.alibaba.fastjson.JSON;
import com.gs.bean.Admin;
import com.gs.bean.News;
import com.gs.common.CommonMethod;
import com.gs.common.Constants;
import com.gs.common.WebUtil;
import com.gs.common.util.FileUtil;
import com.gs.service.NewsService;
import com.gs.service.impl.NewsServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang Genshen on 2017-07-17.
 */
@WebServlet(name = "NewsServlet", urlPatterns = "/news/*")
public class NewsServlet extends HttpServlet {
    private static final long serialVersionUID = 6217730300422759573L;

    private NewsService newsServiceImpl;

    public NewsServlet() {
        newsServiceImpl = new NewsServiceImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String method = WebUtil.getReqMethod(req);
        if (method.equals("list")) {
            list(req, resp);
        } else if (method.equals("detail")) {
            detail(req, resp);
        } else {
            HttpSession session = req.getSession();
            Admin adm = (Admin) session.getAttribute("admin");
            if (adm == null) {
                resp.sendRedirect("../front/index");
            } else {
                if (method.equals("add_page")) {
                    showAddPage(req, resp);
                } else if (method.equals("edit_page")) {
                    showEditPage(req, resp);
                } else if (method.equals("add")) {
                    add(req, resp);
                } else if (method.equals("edit")) {
                    edit(req, resp);
                } else if (method.equals("remove")) {
                    remove(req, resp);
                } else if (method.equals("admin_list")) {
                    adminList(req, resp);
                } else if (method.equals("admin_detail")) {
                    adminDetail(req, resp);
                }
            }
        }
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CommonMethod.newsDetailCommon(req,resp,newsServiceImpl);
        req.getRequestDispatcher("/WEB-INF/views/front/news.jsp").forward(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CommonMethod.newsListCommon(req,resp,newsServiceImpl);
        req.setAttribute(Constants.CURRENT_PAGE, "news");
        req.getRequestDispatcher("/WEB-INF/views/front/news_list.jsp").forward(req, resp);
    }

    private void adminDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CommonMethod.newsDetailCommon(req,resp,newsServiceImpl);
        req.setAttribute(Constants.CURREANT_ADMIN_PAGE, "news");
        req.getRequestDispatcher("/WEB-INF/views/news/news.jsp").forward(req, resp);
    }

    private void adminList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CommonMethod.newsListCommon(req,resp,newsServiceImpl);
        req.setAttribute(Constants.CURREANT_ADMIN_PAGE, "news");
        req.getRequestDispatcher("/WEB-INF/views/news/news_list.jsp").forward(req, resp);
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Map<String, String> map = new HashMap<String, String>();
        String idStr = req.getParameter("id");
        if(idStr != null) {
            int id = Integer.valueOf(idStr);
            newsServiceImpl.remove(id);
            map.put("error", "删除成功");
            out.write(JSON.toJSONString(map));
        } else {
            map.put("error", "删除失败");
            out.write(JSON.toJSONString(map));
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Map<String, String> map = new HashMap<String, String>();
        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("utf-8");
            News news = new News();
            try {
                List<FileItem> items = upload.parseRequest(req);
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        if (name.equals("title")) {
                            if (item.getString("utf-8") == null || item.getString("utf-8").trim().equals("")) {
                                map.put("error", "请输入新闻标题!");
                                out.write(JSON.toJSONString(map));
                                return;
                            }
                            news.setTitle(item.getString("utf-8"));
                        } else if (name.equals("abstracts")) {
                            if (item.getString("utf-8") == null || item.getString("utf-8").trim().equals("")) {
                                map.put("error", "请输入新闻摘要!");
                                out.write(JSON.toJSONString(map));
                                return;
                            }
                            news.setAbstracts(item.getString("utf-8"));
                        } else if (name.equals("content")) {
                            if (item.getString("utf-8") == null || item.getString("utf-8").trim().equals("")) {
                                map.put("error", "请输入新闻详情!");
                                out.write(JSON.toJSONString(map));
                                return;
                            }
                            news.setContent(item.getString("utf-8"));
                        } else if (name.equals("id")) {
                            news.setId(Integer.valueOf(item.getString("utf-8")));
                        }
                    } else { // 文件
                        String name = item.getFieldName();
                        if (name.equals("image")) {
                            FileUtil.save(req, item);
                            news.setImage("uploads/" + item.getName());
                        }
                    }
                }
                News newes = newsServiceImpl.queryById(news.getId());// 如果没有选择图片，则用使用之前的图片
                if (news.getImage() == null) {
                    news.setImage(newes.getImage());
                }
                newsServiceImpl.update(news);
                resp.setContentType("json");
                map.put("error", "修改成功");
                out.write(JSON.toJSONString(map));
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Map<String, String> map = new HashMap<String, String>();
        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("utf-8");
            News news = new News();
            try {
                List<FileItem> items = upload.parseRequest(req);
                for (FileItem item : items) {
                    if (item.isFormField()) { //表单域
                        String name = item.getFieldName();
                        if (name.equals("title")) {
                            if (item.getString("utf-8") == null || item.getString("utf-8").trim().equals("")) {
                                map.put("error", "请输入新闻标题!");
                                out.write(JSON.toJSONString(map));
                                return;
                            }
                            news.setTitle(item.getString("utf-8"));
                        } else if (name.equals("abstracts")) {
                            if (item.getString("utf-8") == null || item.getString("utf-8").trim().equals("")) {
                                map.put("error", "请输入新闻摘要!");
                                out.write(JSON.toJSONString(map));
                                return;
                            }
                            news.setAbstracts(item.getString("utf-8"));
                        } else if (name.equals("content")) {
                            if (item.getString("utf-8") == null || item.getString("utf-8").trim().equals("")) {
                                map.put("error", "请输入新闻详情!");
                                out.write(JSON.toJSONString(map));
                                return;
                            }
                            news.setContent(item.getString("utf-8"));
                        }
                    } else { // 文件
                        String name = item.getFieldName();
                        if (name.equals("image")) {
                            FileUtil.save(req, item);
                            news.setImage("uploads/" + item.getName());
                        }
                    }
                }
                if (news.getImage() == null) {
                    news.setImage("images/newsDefault.jpg");
                }
                newsServiceImpl.add(news);
                resp.setContentType("json");
                map.put("error", "添加成功");
                out.write(JSON.toJSONString(map));
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void showEditPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id = Integer.valueOf(idStr);
        News news = newsServiceImpl.queryById(id);
        req.setAttribute("news",news);
        req.setAttribute(Constants.CURREANT_ADMIN_PAGE, "news");
        req.getRequestDispatcher("/WEB-INF/views/news/edit_news.jsp").forward(req, resp);
    }

    private void showAddPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute(Constants.CURREANT_ADMIN_PAGE, "addNews");
        req.getRequestDispatcher("/WEB-INF/views/news/add_news.jsp").forward(req, resp);
    }
}