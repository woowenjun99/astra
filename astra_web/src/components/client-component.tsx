"use client";
import { auth } from "@/util/firebase";
import { onAuthStateChanged } from "firebase/auth";
import { redirect, usePathname } from "next/navigation";
import { useEffect, useState, type FC, type ReactNode } from "react";

type ClientComponentProps = {
  children: ReactNode;
};

const ClientComponent: FC<ClientComponentProps> = ({ children }) => {
  const pathName = usePathname();
  const [isAuthStateReady, setIsAuthStateReady] = useState(false);

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, (user) => {
      switch (pathName) {
        case "/auth":
          if (user !== null) redirect("/main");
          break;
        case "/":
          // Allow anyone to access the home page
          break;
        default:
          // Allow anyone to access the documentation page
          if (pathName?.startsWith("/docs")) break;
          if (user === null) redirect("/auth");
      }
      setIsAuthStateReady(true);
    });
    return () => unsubscribe();
  }, [pathName]);

  if (!isAuthStateReady) return <></>;
  return children;
};

export default ClientComponent;
