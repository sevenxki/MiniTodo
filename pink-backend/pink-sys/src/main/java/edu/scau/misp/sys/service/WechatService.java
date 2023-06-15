package edu.scau.misp.sys.service;

import edu.scau.misp.sys.entity.WechatLoginRequest;
import edu.scau.misp.todo.model.IdModel;
import edu.scau.misp.todo.model.UserInforModel;

public interface WechatService {
    IdModel getUserInfoMap(WechatLoginRequest loginRequest) throws Exception;
}

