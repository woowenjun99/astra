import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { signout } from "@/services/authentication/data/authentication-repository";
import Link from "next/link";
import { sendTestNotification } from "@/services/devices/data/device_repository";

export function AppSidebar() {
  return (
    <Sidebar>
      <SidebarContent>
        {/* Application Group */}
        <SidebarGroup>
          <SidebarGroupLabel>Application</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              <SidebarMenuItem>
                <SidebarMenuButton asChild>
                  <Link href="/main">Home</Link>
                </SidebarMenuButton>
                <SidebarMenuButton asChild>
                  <Link href="/main/workout">Fitness</Link>
                </SidebarMenuButton>
                <SidebarMenuButton asChild>
                  <Link href="/docs">Documentation</Link>
                </SidebarMenuButton>
              </SidebarMenuItem>
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        {/* Settings Group */}
        <SidebarGroup>
          <SidebarGroupLabel>Settings</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              <SidebarMenuItem>
                <SidebarMenuButton onClick={sendTestNotification}>
                  Test Notification
                </SidebarMenuButton>
                <SidebarMenuButton asChild></SidebarMenuButton>
                <SidebarMenuButton onClick={signout}>Logout</SidebarMenuButton>
              </SidebarMenuItem>
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}
