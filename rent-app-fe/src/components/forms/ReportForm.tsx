import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Button, useToast } from "@/components/ui";
import axios from "axios";
import { auth } from "@/configs/firebase";
import { authApi, endpoints } from "@/configs/APIs";

type ReportFormProps = {
  postId: string;
  onClose: () => void;
};

const ReportForm: React.FC<ReportFormProps> = ({ postId, onClose }) => {
  const { register, handleSubmit, reset } = useForm();
  const { toast } = useToast();
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]);

  const handleOptionToggle = (option: string) => {
    const isSelected = selectedOptions.includes(option);
    if (isSelected) {
      setSelectedOptions(selectedOptions.filter((item) => item !== option));
    } else {
      setSelectedOptions([...selectedOptions, option]);
    }
  };

  const submitForm = async () => {
    try {
      const formData = {
        postId,
        reason: selectedOptions.join(", "),
      };

      let res = await authApi().post(endpoints["report-post"], formData);
      if (res.status === 200) {
        reset();

        toast({
          title: "Form submitted",
          description: "Report submitted successfully.",
        });
      }
      onClose();
    } catch (error) {
      console.error("Error submitting report:", error);
      toast({
        title: "Error",
        description: "Failed to submit report. Please try again later.",
      });
    }
  };

  return (
    <form
      onSubmit={handleSubmit(submitForm)}
      className="max-w-lg mx-auto mt-8 bg-black"
    >
      <div className="mb-4 bg-black">
        <p className="text-2xl font-bold mb-3">Report this post to Admin</p>
        <p className="block text-sm font-medium text-off-white mb-2">
          Select Report Type:
        </p>
        <div className="flex gap-4">
          <button
            type="button"
            className={`px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium focus:outline-none ${
              selectedOptions.includes("spam")
                ? "bg-indigo-600 text-white"
                : "bg-gray-950 text-off-white"
            }`}
            onClick={() => handleOptionToggle("spam")}
          >
            Spam
          </button>

          <button
            type="button"
            className={`px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium focus:outline-none ${
              selectedOptions.includes("duplicate")
                ? "bg-indigo-600 text-white"
                : "bg-gray-950 text-off-white"
            }`}
            onClick={() => handleOptionToggle("duplicate")}
          >
            Duplicate
          </button>

          <button
            type="button"
            className={`px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium focus:outline-none ${
              selectedOptions.includes("inappropriate")
                ? "bg-indigo-600 text-white"
                : "bg-gray-950 text-off-white"
            }`}
            onClick={() => handleOptionToggle("inappropriate")}
          >
            Inappropriate Content
          </button>
          <button
            type="button"
            className={`px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium focus:outline-none ${
              selectedOptions.includes("invalid address")
                ? "bg-indigo-600 text-white"
                : "bg-gray-950 text-off-white"
            }`}
            onClick={() => handleOptionToggle("invalid address")}
          >
            Invalide Address
          </button>
        </div>
      </div>

      <div className="mb-4">
        <label
          htmlFor="details"
          className="block text-sm font-medium text-off-white"
        >
          Other Details (Optional)
        </label>
        <textarea
          id="details"
          {...register("details")}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 bg-gray-950 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm resize-none"
          rows={3}
        ></textarea>
      </div>

      <div className="text-right">
        <Button className="shad-btn_dark_4" onClick={onClose}>
          Close
        </Button>
        <button
          type="submit"
          className="inline-flex items-center justify-center px-4 py-2 border border-transparent rounded-md shadow-sm text-base font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          Submit
        </button>
      </div>
    </form>
  );
};

export default ReportForm;
