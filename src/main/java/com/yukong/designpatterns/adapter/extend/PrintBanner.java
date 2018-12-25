package com.yukong.designpatterns.adapter.extend;

import com.yukong.designpatterns.adapter.Banner;

/**
 * @author: yukong
 * @date: 2018/12/25 14:11
 * 适配后的对象
 */
public class PrintBanner extends Banner implements Print {


    public PrintBanner(String string) {
        super(string);
    }

    @Override
    public void printWeak() {
        super.showWithParen();
    }

    @Override
    public void printStrong() {
        showWithAster();
    }
}
