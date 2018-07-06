package com.coder.util.page;

/**
 * @Author: liuhao
 * @Descirption: 使用Easyui分页组件必用的几个属性封装
 * @Date: 2018/7/6_15:04
 */
public class PageBean {
    private int page;//第几页
    private int rows;//每页显示几行,相当于之前的pageSize
    private int start;//起始行
    public PageBean() {
        super();
    }
    public PageBean(int page, int rows) {
        super();
        this.page = page;
        this.rows = rows;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getStart() {
        return (page-1)*rows;
    }
    public void setStart(int start) {
        this.start = start;
    }
    @Override
    public String toString() {
        return "PageBean [page=" + page + ", rows=" + rows + ", start=" + start + "]";
    }
}
