import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import { SiShopware } from 'react-icons/si';
import { MdOutlineCancel } from 'react-icons/md';
import { TooltipComponent } from '@syncfusion/ej2-react-popups';

import { links } from '../../assets/dummy.js';
import { useStateContext } from '../../contexts/ContextProvider';

const Sidebar = () => {

    const { currentColor, activeMenu, setActiveMenu, screenSize } = useStateContext();

    const handleCloseSideBar = () => {
      if (activeMenu !== undefined && screenSize <= 900) {
        setActiveMenu(false);
      }
    };
    const activeLink = 'flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg  text-white  text-md m-2';
    const normalLink = 'flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg  text-md text-gray-100 dark:text-gray-100 dark:hover:text-black hover:bg-blue-100 m-2';

  return (
    <div className="pl-3 h-screen md:overflow-hidden overflow-auto md:hover:overflow-auto pb-10 bg-slate-600">
    {activeMenu && (
      <>
        <div className="flex justify-between items-center">
          <Link to="/" onClick={handleCloseSideBar} className="items-center gap-3 ml-3 mt-4 flex text-xl font-extrabold tracking-tight dark:text-white text-slate-900">
            <span className='text-3xl text-white font-thin'>MEDFRIEND.</span>
          </Link>
          <TooltipComponent content="Menu" position="BottomCenter">
            <button
              type="button"
              onClick={() => setActiveMenu(!activeMenu)}
              style={{ color: currentColor }}
              className="text-xl rounded-full p-3 hover:bg-light-gray mt-4 block md:hidden border-none"
            >
              <MdOutlineCancel />
            </button>
          </TooltipComponent>
        </div>
        <div className="mt-10 ">
          {links.map((item) => (
            <div key={item.title}>
              <p className="text-white dark:text-white m-3 mt-4 uppercase">
                {item.title}
              </p>
              {item.links.map((link) => (
                <NavLink
                  to={`/dashboard/${link.name}`}
                  key={link.name}
                  onClick={handleCloseSideBar}
                  style={({ isActive }) => ({
                    backgroundColor: isActive ? currentColor : '',
                  })}
                  className={({ isActive }) => (isActive ? activeLink : normalLink)}
                >
                  {link.icon}
                  <span className="capitalize ">{link.name}</span>
                </NavLink>
              ))}
            </div>
          ))}
        </div>
      </>
    )}
  </div>
  );
};

export default Sidebar;