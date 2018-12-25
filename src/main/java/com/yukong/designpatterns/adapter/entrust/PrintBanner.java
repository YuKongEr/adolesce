package com.yukong.designpatterns.adapter.entrust;

import com.yukong.designpatterns.adapter.Banner;

/**
 * @author: yukong
 * @date: 2018/12/25 14:19
 * 适配后的对象
 */
public class PrintBanner extends Print {

    private Banner banner;

    public PrintBanner(Banner banner) {
        this.banner = banner;
    }

    @Override
    public void printWeak() {
        banner.showWithParen();
    }

    @Override
    public void printStrong() {
        banner.showWithAster();
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }
}
