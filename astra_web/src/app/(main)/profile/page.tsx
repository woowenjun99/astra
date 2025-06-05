"use client";
import ProfileForm from "@/component/user/ProfileForm";
import { Tabs } from "@mantine/core";

export default function Page() {
  return (
    <div style={{ width: "100%", margin: 16 }}>
      <Tabs defaultValue="personal" variant="pills">
        <Tabs.List justify="center" grow>
          <Tabs.Tab value="personal">Personal</Tabs.Tab>
          <Tabs.Tab value="stats">Physical Stats</Tabs.Tab>
          <Tabs.Tab value="fitness">Fitness Goals</Tabs.Tab>
          <Tabs.Tab value="account">Account</Tabs.Tab>
        </Tabs.List>

        <Tabs.Panel value="personal">
          <ProfileForm />
        </Tabs.Panel>
      </Tabs>
    </div>
  );
}
