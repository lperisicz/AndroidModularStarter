package com.perisic.luka.repository.repos.abstraction;

import androidx.lifecycle.LiveData;

import com.perisic.luka.remote.data.helper.BaseResponse;
import com.perisic.luka.remote.data.request.LoginRequest;
import com.perisic.luka.remote.data.response.LoginResponse;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public interface AuthRepository {

    LiveData<BaseResponse<LoginResponse>> login(LoginRequest loginRequest);

}