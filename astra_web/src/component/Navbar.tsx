"use client";
import { useState } from "react";
import {
  IconCalendar,
  IconDatabaseImport,
  IconHome,
  IconLogout,
  IconReceipt2,
  IconSettings,
  IconSwitchHorizontal,
} from "@tabler/icons-react";
import { Code, Group } from "@mantine/core";
import classes from "./Navbar.module.css";
import { signout } from "@/api/user";
import Link from "next/link";

const data = [
  { link: "/", label: "Dashboard", icon: IconHome },
  { link: "", label: "Statistics", icon: IconReceipt2 },
  { link: "", label: "Calendar", icon: IconCalendar },
  { link: "", label: "Databases", icon: IconDatabaseImport },
  { link: "/profile", label: "Settings", icon: IconSettings },
];

export function NavbarSimple() {
  const [active, setActive] = useState("Dashboard");

  const links = data.map((item) => (
    <Link
      href={item.link}
      onClick={() => setActive(item.label)}
      key={item.label}
      className={classes.link}
      data-active={item.label === active || undefined}
    >
      <item.icon className={classes.linkIcon} stroke={1.5} />
      <span>{item.label}</span>
    </Link>
  ));

  return (
    <nav className={classes.navbar}>
      <div className={classes.navbarMain}>
        <Group className={classes.header} justify="space-between">
          <Code fw={700}>v0.1.0</Code>
        </Group>
        {links}
      </div>

      <div className={classes.footer}>
        <a
          href="#"
          className={classes.link}
          onClick={(event) => event.preventDefault()}
        >
          <IconSwitchHorizontal className={classes.linkIcon} stroke={1.5} />
          <span>Change account</span>
        </a>

        <a href="#" className={classes.link} onClick={signout}>
          <IconLogout className={classes.linkIcon} stroke={1.5} />
          <span>Logout</span>
        </a>
      </div>
    </nav>
  );
}
