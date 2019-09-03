package com.perisic.luka.repository.repos.abstraction;

import androidx.lifecycle.LiveData;

import com.perisic.luka.remote.data.helper.BaseResponse;
import com.perisic.luka.remote.data.request.LoginRequest;
import com.perisic.luka.remote.data.request.RegisterRequest;
import com.perisic.luka.remote.data.response.LoginResponse;
import com.perisic.luka.remote.data.response.RegisterResponse;
import com.perisic.luka.repository.repos.BaseRepository;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public interface AuthRepository extends BaseRepository {

    LiveData<BaseResponse<LoginResponse>> login(LoginRequest loginRequest);

    LiveData<BaseResponse<RegisterResponse>> register(RegisterRequest registerRequest);

}