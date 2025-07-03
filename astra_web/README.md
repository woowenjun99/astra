# Astra Web

Astra is a Progressive Web App that is built with NextJS. It uses Firebase for
Authentication and Push Notification, and a backend server which can be found
in the same repository.

## Setup

1. Run `bun install` to install the necessary dependencies.
2. Create a `.env` file
3. Create a Firebase Project and a web app.
4. Fill up the following parameters in the `.env` file

   ```.env
   NEXT_PUBLIC_FIREBASE_API_KEY=
   NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN=
   NEXT_PUBLIC_FIREBASE_PROJECT_ID=
   NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET=
   NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID=
   NEXT_PUBLIC_FIREBASE_APP_ID=
   NEXT_PUBLIC_FIREBASE_CLOUD_MESSAGING_VAPID_KEY=
   ```

5. Start the backend server.
6. Run `bun run dev` to start the local web app.
