"use client";
import { Burger, Button, Container, Drawer, Stack } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import classes from "./Header.module.css";
import Link from "next/link";
import { signout } from "@/services/authentication/data/authentication-api";

const links = [
  { link: "/main/", label: "Fitness" },
  {
    link: "/main/profile",
    label: "Settings",
  },
];

export default function Header() {
  const [opened, { toggle, close }] = useDisclosure(false);

  return (
    <header className={classes.header}>
      <Container size="md">
        <div className={classes.inner}>
          <Burger opened={opened} onClick={toggle} size="sm" hiddenFrom="sm" />
        </div>
        <Drawer opened={opened} onClose={close} size="md">
          <Stack>
            {links.map((link) => {
              return (
                <Link
                  key={link.label}
                  href={link.link}
                  passHref
                  style={{ textDecoration: "none" }}
                  onClick={close}
                >
                  <Button fullWidth variant="transparent">
                    {link.label}
                  </Button>
                </Link>
              );
            })}
            <Button fullWidth variant="transparent" onClick={signout}>
              Sign Out
            </Button>
          </Stack>
        </Drawer>
      </Container>
    </header>
  );
}
