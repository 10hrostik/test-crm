import React, {useEffect} from "react";
import './styles/greetLayout/sideBar.css';
import './styles/greetLayout/topBar.css';
import './styles/buttons.css'

function GreetLayout(props) {
  const user = props.user;
  const setUser = props.setUser;

  useEffect(() => {
      setUser(user);
  }, [user, setUser])

  const toggleSideBar = () => {
    let sidebar = document.querySelector('.sideBar');
    let sidebarWidth = sidebar.offsetWidth;
    if (sidebar.style.left === '0px')
      sidebar.style.left = `-${sidebarWidth}px`;
    else
      sidebar.style.left = '0px';
  }

  return (
      <>
        <div id='topBar' className='topBar'>
          <button onClick={toggleSideBar} id='menuButton' className='topBarButton'>
            <img id='menuImage' className='topBarImage' src={require('./images/menu.png')} height={35} width={30} alt={''}></img>
          </button>
        </div>
        <div id='sideBar' className="sideBar">
        </div>
        <div id="chatLayout" className="chatLayout"></div>
      </>
  )
}

export default GreetLayout;