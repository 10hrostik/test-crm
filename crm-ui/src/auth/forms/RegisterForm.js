import React from "react";
import apiServer from "../../utils/ApiServer";
import generateStatusTag from "../../utils/StatusTag";

function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validatePhone(phone) {
  return phone.length === 10;
}

function validatePassword(password) {
  if (password.length < 6) {
    return false;
  }
  return /\d/.test(password);
}


function RegisterForm(props) {
  const registerUrl = apiServer + '/public/auth/register';

  const buildRequest = (username, password, confirmPassword) => {
    let registerData = {};
    if (validatePassword(password)) {
      registerData.username = username;
      registerData.password = password;
      registerData.confirmPassword = confirmPassword;
      return registerData;
    } else {
      if (document.getElementById('wrongRegisterPhoneInput')) document.getElementById('wrongRegisterPhoneInput').remove();
      if (document.getElementById('wrongRegisterEmailInput')) document.getElementById('wrongRegisterEmailInput').remove();
      if (!document.getElementById('wrongRegisterPasswordInput'))
        generateStatusTag('wrongRegisterPasswordInput', 'wrongInput', 'Password should be 6 chars long and contains a digit!',
            'registerForm');
    }
  }

  const register = (event) => {
    event.preventDefault();
    let registerData = buildRequest(event.target.credential.value.trim(), event.target.password.value.trim(), event.target.confirmPassword.value.trim());
    if (!registerData) return;
    fetch(registerUrl, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(registerData)
    }).then((response) => response.json())
    .then(response => {
      if (response.code === 400) throw new Error(response.messages[0]);
      sessionStorage.setItem('token', response.token);
      props.setUser(response);
    })
    .catch(e => {
      if (!document.getElementById('wrongRegisterInput')) {
        generateStatusTag('wrongRegisterInput', 'wrongInput', e.message, 'registerForm');
        console.log(e)
      }
    })
  }

  const handleAuthLayout = () => {
    props.toggleToLogin(false);
  }

  return (
      <div className={'authFormPopUp'}>
        <h1>CRM</h1>
        <form id={'registerForm'} onSubmit={register} method="POST">
          <input id="registerCredential" className={'authInput'} type="text" placeholder={'username'}
                 name="credential" required></input>
          <br/>
          <input id="registerPassword" className={'authInput'} style={{marginBottom: 5}}
                 type="password" placeholder={'password'} name="password" required></input>
          <br/>
          <input id="registerConfirmedPassword" className={'authInput'} style={{marginBottom: 5}}
                 type="password" placeholder={'confirm password'} name="confirmPassword" required></input>
          <br/>
          <b onClick={handleAuthLayout} className={'forgotPasswordLabel'}>Back to login</b>
          <br/>
          <button id="registerButton" className={'registerButton'} type="submit">Register</button>
        </form>
      </div>
  )
}

export default RegisterForm