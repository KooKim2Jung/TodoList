import React, { useState } from 'react';
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

    const [emailEroor, setEmailError] = useState('');
    const [passwdError, setPasswdError] = useState('');
    const [passwdConfirmError, setPasswdConfirmError] = useState('');
    const [nickNameError, setNickNameError] = useState('');
    const [phoneError, setPhoneError] = useState('');

    const resetError = () => {
        setPasswdError('');
        setPasswdConfirmError('');
        setPhoneError('');
        setNickNameError('');
        setEmailError('');
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

    const submitEdit = (event) => {
        event.preventDefault();
        if (validateForm()) {
            navigate("/TodoList");
        }
    };

    const submitUser = (e) => {
        const { name, value } = e.target;
        setUser(user => ({
          ...user,
          [name]: value,
        }));
      };

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
            <div className='email-box'>
                <div>이메일</div>
                </div>
                <div className='input-box'>
                <div className='errormsg'>{nickNameError}</div>
                    <input 
                        type="text" 
                        value={user.nickName} 
                        onChange={(submitUser)}
                        placeholder='닉네임' 
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
                        placeholder='전화번호' 
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