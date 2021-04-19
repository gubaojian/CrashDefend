package com.efurture.wireless.defend.plugin.config.domain;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * 判断是否满足防护方法列表
 * */
public interface MatchDefend {

    /**
     * 判断是否满足防护方法列表
     * */
    public boolean isDefend(CtClass ctClass, CtMethod ctMethod);
}
