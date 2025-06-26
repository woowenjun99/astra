import { IconCheck } from "@tabler/icons-react";
import {
  Button,
  Container,
  Group,
  Image,
  List,
  Text,
  ThemeIcon,
  Title,
} from "@mantine/core";
import classes from "./HeroBullets.module.css";
import Link from "next/link";

export function HeroBullets() {
  return (
    <Container size="md">
      <div className={classes.inner}>
        <div className={classes.content}>
          <Title className={classes.title}>Astra</Title>
          <Text c="dimmed" mt="md">
            A powerful super app that helps with many aspects of life.
          </Text>

          <List
            mt={30}
            spacing="sm"
            size="sm"
            icon={
              <ThemeIcon size={20} radius="xl">
                <IconCheck size={12} stroke={1.5} />
              </ThemeIcon>
            }
          >
            <List.Item>
              <b>Fitness Tracking</b> – Keep track of your workouts
            </List.Item>
          </List>

          <Group mt={30}>
            <Link passHref href="/main">
              <Button
                radius="xl"
                size="md"
                className={classes.control}
                color="black"
              >
                Get started
              </Button>
            </Link>
            <Link
              passHref
              href="https://github.com/woowenjun99/astra/tree/master"
            >
              <Button
                variant="default"
                radius="xl"
                size="md"
                className={classes.control}
              >
                Source code
              </Button>
            </Link>
          </Group>
        </div>
        <Image
          alt="Home"
          className={classes.image}
          src="undraw_smart-home_9s59.svg"
        />
      </div>
    </Container>
  );
}
