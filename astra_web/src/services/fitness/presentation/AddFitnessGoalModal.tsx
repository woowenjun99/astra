"use client";
import { useDisclosure } from "@mantine/hooks";
import {
  Button,
  Grid,
  Modal,
  NumberInput,
  Select,
  Textarea,
  TextInput,
} from "@mantine/core";
import { IconPlus } from "@tabler/icons-react";
import { DateInput } from "@mantine/dates";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { notifications } from "@mantine/notifications";
import { createFitnessGoal } from "../data/fitness-api";
import dayjs from "dayjs";
import { useMediaQuery } from "@mantine/hooks";

const schema = z.object({
  category: z.string(),
  description: z.string().nullable(),
  targetDate: z.string(),
  targetValue: z.number(),
  title: z.string(),
});

type Schema = z.infer<typeof schema>;

export default function AddFitnessGoalModal() {
  const [opened, { open, close }] = useDisclosure(false);
  const isMdScreen = useMediaQuery("(min-width: 62em)");
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
    watch,
    setValue,
  } = useForm<Schema>({
    defaultValues: {
      description: "",
      targetDate: dayjs().format("YYYY-MM-DD"),
      title: "",
    },
    resolver: zodResolver(schema),
  });

  const onSubmit = handleSubmit(async (values) => {
    try {
      await createFitnessGoal({
        category: values.category,
        description: values.description?.trim() ?? null,
        targetDate: dayjs(values.targetDate, "YYYY-MM-DD").toDate(),
        targetValue: values.targetValue,
        title: values.title.trim(),
      });
      notifications.show({
        color: "green",
        message: "You have created a new fitness goal!",
        title: "Success",
      });
    } catch (e) {
      notifications.show({
        color: "red",
        title: "Error",
        message: (e as Error).message,
      });
    }
  });

  return (
    <>
      <Modal opened={opened} onClose={close}>
        <form onSubmit={onSubmit}>
          <Grid>
            <Grid.Col>
              <TextInput
                label="Goal Title"
                required
                error={errors.title?.message}
                placeholder="e.g. Weight Loss, Run 5 KM"
                {...register("title")}
              />
            </Grid.Col>
            <Grid.Col span={{ sm: 12, md: 6 }}>
              <Select
                label="Category"
                required
                value={watch("category")}
                data={["Distance", "Frequency", "Weight"]}
                onChange={(value) => {
                  if (value === null) return;
                  setValue("category", value);
                }}
              />
            </Grid.Col>
            <Grid.Col span={{ sm: 12, md: 6 }}>
              <DateInput
                label="Target Date (Optional)"
                onChange={(value) => {
                  if (value === null) return;
                  setValue("targetDate", value);
                }}
                value={watch("targetDate")}
              />
            </Grid.Col>
            <Grid.Col>
              <NumberInput
                label="Target Value"
                required
                allowNegative={false}
                decimalScale={2}
                value={watch("targetValue")}
                error={errors.targetValue?.message}
                onChange={(value) => {
                  if (value === null) return;
                  if (typeof value === "string") setValue("targetValue", 0);
                  else setValue("targetValue", value);
                }}
              />
            </Grid.Col>
            <Grid.Col>
              <Textarea
                label="Description (Optional)"
                placeholder="Brief description of your goal"
                {...register("description")}
              />
            </Grid.Col>
          </Grid>
          <div
            style={{
              display: "flex",
              flexDirection: "row",
              justifyContent: "end",
            }}
          >
            <Button
              variant="outline"
              mt="lg"
              type="button"
              onClick={close}
              mr="md"
              color="black"
            >
              Cancel
            </Button>
            <Button
              color="black"
              type="submit"
              mt="lg"
              loading={isSubmitting}
              disabled={isSubmitting}
            >
              Create Goal
            </Button>
          </div>
        </form>
      </Modal>
      <Button
        onClick={open}
        color="black"
        leftSection={<IconPlus />}
        fullWidth={!isMdScreen}
      >
        Add New Goals
      </Button>
    </>
  );
}
