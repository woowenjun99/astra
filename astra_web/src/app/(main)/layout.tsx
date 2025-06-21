import Header from "@/component/Header";
import { NavbarSimple } from "@/component/Navbar";
import "../globals.css";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <Header />
      <div className="flex bg-[#F9FAFB] h-dvh">
        <NavbarSimple />
        <div className="w-3/4 mx-auto pt-5">{children}</div>
      </div>
    </div>
  );
}
