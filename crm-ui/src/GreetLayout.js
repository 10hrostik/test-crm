import React, {useEffect, useState} from "react";
import './styles/greetLayout/sideBar.css';
import './styles/greetLayout/topBar.css';
import './styles/greetLayout/crmLayout.css';
import './styles/buttons.css'
import apiServer from "./utils/ApiServer";

function GreetLayout(props) {
  const user = props.user;
  const setUser = props.setUser;
  const [tasks, setTasks] = useState([]);
  const [contacts, setContacts] = useState([]);

  useEffect(() => {
      setUser(user);
      (async() => {
        const tasks = await fetch(apiServer + '/secured/v1/tasks/all', {
          headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
          },
          method: "GET"
        })
            .then(response => response.json())
            .catch(e => console.log(e));
        setTasks(tasks);
      })();

      (async() => {
        const contacts = await fetch(apiServer + '/secured/v1/contacts/all', {
          headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
          },
          method: "GET"
        })
            .then(response => response.json())
            .catch(e => console.log(e));
        setContacts(contacts);
      })()
  }, [user, setUser])

  const toggleSideBar = () => {
    let sidebar = document.querySelector('.sideBar');
    let sidebarWidth = sidebar.offsetWidth;
    if (sidebar.style.left === '0px')
      sidebar.style.left = `-${sidebarWidth}px`;
    else
      sidebar.style.left = '0px';
  }

  const getTasks = () => {
    return tasks.map(task => <ul key={task.id}>{task.description + " " + task.status}</ul>)
  }

  const getContacts = () => {
    return contacts.map(contact => <ul key={contact.id}>{contact.name + " " + contact.surname}</ul>)
  }

  return (
      <>
        <div id='topBar' className='topBar'>
          <button onClick={toggleSideBar} id='menuButton' className='topBarButton'>
            <img id='menuImage' className='topBarImage' src={require('./images/menu.png')} height={35} width={30}
                 alt={''}></img>
          </button>
        </div>
        <div id='sideBar' className="sideBar">
        </div>
        <fieldset style={{
          width: "33%",
          border: "2.5px solid black",
          textAlign: "left"
        }}>
          <legend style={{fontWeight: "bold"}}>Tasks</legend>
          <div id="taskLayout" className="taskLayout">
            <li id="taskTable">
              {getTasks()}
            </li>
          </div>
        </fieldset>
        <fieldset style={{
          width: "33%",
          border: "2.5px solid black",
          textAlign: "left"
        }}>
          <legend style={{fontWeight: "bold"}}>Contacts</legend>
          <div id="taskLayout" className="taskLayout">
            <li id="taskTable">
              {getContacts()}
            </li>
          </div>
        </fieldset>
      </>
  )
}

export default GreetLayout;