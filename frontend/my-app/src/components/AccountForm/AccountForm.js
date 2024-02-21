// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import './AccountForm.css'

// const AccountForm = () => {
//     const [user, setUser] = useState({
//         email: "",
//         passwd: "",
//         passwdConfirm: "",
//         tel: ""
//     });

//     const [emailError, setEmailError] = useState('');
//     const [passwdError, setPasswdError] = useState('');
//     const [passwdConfirmError, setPasswdConfirmError] = useState('');
//     const [telError, setTelError] = useState('');

//     const resetError = () => {
//         setEmailError('');
//         setPasswdError('');
//         setPasswdConfirmError('');
//         setTelError('');
//     }

//     const validateForm = () => {
//         resetError();

//         let validated = true;
//         if (!user.email) {
//             setEmailError('이메일을 입력해주세요.');
//             validated = false;
//         }
//         if (!user.passwd) {
//             setPasswdError('비밀번호를 입력해주세요.');
//             validated = false;
//         }
//         if (!user.passwdConfirm) {
//             setPasswdConfirmError('위의 비밀번호와 동일하게 입력해주세요.');
//             validated = false;
//         }
//         if (user.passwd !== user.passwdConfirm) {
//             setPasswdConfirmError('비밀번호가 일치하지 않습니다.');
//             validated = false;
//         }
//         if (!user.tel) {
//             setTelError('전화번호를 입력해주세요.');
//             validated = false;
//         }
//         return validated;
//     }

//     const navigate = useNavigate();

//     const submitEdit = (event) => {
//         event.preventDefault();
//         if (validateForm()) {
//             navigate("/TodoList");
//         }
//     };

//     const submitUser = (e) => {
//         const { name, value } = e.target;
//         setUser(user => ({
//           ...user,
//           [name]: value,
//         }));
//       };


//     return (
//         <div className='Account'>
//             <h1>Account</h1>
//             <form onSubmit={submitEdit}>
//                 <div className='input-box'>
//                     <input 
//                         type="email" 
//                         value={user.email} 
//                         onChange={(e) => setUser(e.target.value)}
//                         placeholder='사용자 이메일' 
//                         required
//                     />
//                 </div>
//                 <div className='input-box'>
//                     <input 
//                         type="password"
//                         value={user.passwd}
//                         placeholder='비밀번호' 
//                         name="passwd"
//                         required
//                     />
//                 </div>
//                 <div className='input-box'>
//                     <input 
//                         type="password"
//                         value={user.passwdConfirm}
//                         placeholder='비밀번호 확인' 
//                         name="passwdConfirm"
//                         required
//                     />
//                 </div>
//                 <div className='input-box'>
//                     <input 
//                         type="text"
//                         value={user.tel}
//                         placeholder='전화번호' 
//                         name="tel"
//                         required
//                     />
//                 </div>
//                 <p><button type="submit">수정하기</button></p>
//                 <button type="submit">회원탈퇴</button>
//             </form>
//         </div>
//     );
// };

// export default AccountForm;