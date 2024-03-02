import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './AccountForm.css'

const AccountForm = () => {
    const [user, setUser] = useState({
        email: "",
        passwd: "",
        passwdConfirm: "",
        nickName: "",
        phone: ""
    });

    const [passwdError, setPasswdError] = useState('');
    const [passwdConfirmError, setPasswdConfirmError] = useState('');
    const [nickNameError, setNickNameError] = useState('');
    const [phoneError, setPhoneError] = useState('');

    const resetError = () => {
        setPasswdError('');
        setPasswdConfirmError('');
        setPhoneError('');
        setNickNameError('');
    }

    const validateForm = () => {
        resetError();

        let validated = true;
        if (!user.nickName) {
            setNickNameError('닉네임을 입력해주세요.');
            validated = false;
        }
        if (!user.passwd) {
            setPasswdError('비밀번호를 입력해주세요.');
            validated = false;
        }
        if (!user.passwdConfirm) {
            setPasswdConfirmError('위의 비밀번호와 동일하게 입력해주세요.');
            validated = false;
        }
        if (user.passwd !== user.passwdConfirm) {
            setPasswdConfirmError('비밀번호가 일치하지 않습니다.');
            validated = false;
        }
        if (!user.phone) {
            setPhoneError('전화번호를 입력해주세요.');
            validated = false;
        }
        return validated;
    }

    const navigate = useNavigate();

    const submitEdit = async (event) => {
        event.preventDefault();
        if (validateForm()) {
            try {
                const response = await axios.put('http://localhost:8080/api/v1/users', {
                    nickname: user.nickName,
                    password: user.passwd,
                });
                // 응답 처리
                console.log(response.data);
                navigate("/TodoList"); // 성공 시 이동할 경로
            } catch (error) {
                console.error("회원 정보 수정 오류", error);
                // 오류 처리
            }
        }
    };

    const submitUser = (e) => {
        const { name, value } = e.target;
        setUser(user => ({
          ...user,
          [name]: value,
        }));
      };

      const fetchUserData = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/v1/users');
            const userData = response.data;
            setUser({
                ...user,
                email: userData.email,
                nickName: userData.nickname, // 여기서 "nickname"은 API 응답의 필드명에 따라 다를 수 있습니다.
                // 다른 필드도 필요하다면 여기에 추가
            });
        } catch (error) {
            console.error("사용자 데이터 불러오기 오류", error);
            // 오류 처리
        }
    };

    // 컴포넌트가 마운트될 때 사용자 정보를 불러옵니다.
    useEffect(() => {
        fetchUserData();
    }, []);


      const deleteUser = async () => {
        try {
            // DELETE 요청을 보내고 응답을 기다립니다.
            await axios.delete('http://localhost:8080/api/v1/users');
            // 성공적인 응답을 받은 경우 '/' 페이지로 이동합니다.
            navigate('/');
        } catch (error) {
            console.error("회원 탈퇴 요청 오류", error);
            // 오류 처리
        }
    };

    return (
        <div className='Account'>
            <h1>Account</h1>
            <form onSubmit={submitEdit}>
                <div className='input-box'>
                    <input 
                        type="email" 
                        value={user.email} 
                        onChange={(submitUser)}
                        name="email"
                        disabled
                    />
                </div>
                <div className='input-box'>
                <div className='errormsg'>{nickNameError}</div>
                    <input 
                        type="text" 
                        value={user.nickName} 
                        onChange={(submitUser)}
                        placeholder='닉네임' 
                        name="nickName"
                    />
                </div>
                <div className='input-box'>
                <div className='errormsg'>{passwdError}</div>
                    <input 
                        type="password"
                        value={user.passwd}
                        onChange={(submitUser)}
                        placeholder='비밀번호' 
                        name="passwd"
                    />
                </div>
                <div className='input-box'>
                <div className='errormsg'>{passwdConfirmError}</div>
                    <input 
                        type="password"
                        value={user.passwdConfirm}
                        onChange={(submitUser)}
                        placeholder='비밀번호 확인' 
                        name="passwdConfirm"
                    />
                </div>
                <div className='input-box'>
                <div className='errormsg'>{phoneError}</div>
                    <input 
                        type="text"
                        value={user.phone}
                        onChange={(submitUser)}
                        pattern="\d*" minlength="10" maxlength="11"
                        placeholder='010-1234-5678 ( &lsquo;-&rsquo; 없이 입력 )'
                        name="phone"
                    />
                </div>
                <p><button className='sub-btn' type="submit">수정하기</button></p>
                <button className="sub-btn" onClick={deleteUser}>회원탈퇴</button>
            </form>
        </div>
    );
};

export default AccountForm;