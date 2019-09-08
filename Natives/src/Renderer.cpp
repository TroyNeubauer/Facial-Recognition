#include "Renderer.h"

#include <glad/glad.h>
#include "Natives.h"
#include "imgui.h"
#include "imgui_impl_glfw.h"
#include "imgui_impl_opengl3.h"

void Renderer::Init()
{
	if (!gladLoadGL())
	{
		ThrowError("Failed to load GLAD");
	}
	IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGuiIO& io = ImGui::GetIO(); (void)io;
	io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;     // Enable Keyboard Controls

	ImGui::StyleColorsDark();
	
	// Setup Platform/Renderer bindings
	ImGui_ImplGlfw_InitForOpenGL();
	ImGui_ImplOpenGL3_Init("#version 130");
}

void Renderer::Render(int width, int height)
{
	/*char clipboard[256], buf[512];
	GetClipboard(clipboard, sizeof(clipboard));
	snprintf(buf, sizeof(buf), "Clipboard is: %s", clipboard);

	JavaPrint(buf);*/
	ImGui_ImplOpenGL3_NewFrame();
	ImGui_ImplGlfw_NewFrame(width, height);
	ImGui::NewFrame();


	ImGui::ShowDemoWindow();

	//Render other stuff

	// Rendering
	ImGui::Render();
	int w, h;
	glViewport(0, 0, width, height);
	glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT);

	ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());
}
