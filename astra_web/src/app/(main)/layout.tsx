import { NavbarSimple } from "@/component/Navbar";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div style={{ display: "flex" }}>
      <NavbarSimple />
      {children}
    </div>
  );
}
