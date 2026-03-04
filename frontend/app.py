import streamlit as st
import subprocess
import os

st.title("Java Jimple Optimization Viewer")

uploaded = st.file_uploader("Upload Java File", type=["java"])

if uploaded:

    os.makedirs("../input", exist_ok=True)

    path = os.path.join("../input", uploaded.name)

    with open(path, "wb") as f:
        f.write(uploaded.getbuffer())

    st.success("File uploaded")
    print(path)
    subprocess.run(
        [
            "javac",
            "-d",
            "target/input-classes",
            f"input/{path}"
        ],
        cwd=".."
    )

    if st.button("Run Optimizer"):

        result = subprocess.run(
            [
                "mvn",
                "exec:java",
                "-Dexec.mainClass=optimizer.Main"
            ],
            cwd="..",
            capture_output=True,
            text=True
        )

        st.subheader("Optimized Jimple")
        st.code(result.stdout)

        jimple_file = uploaded.name.replace(".java", ".jimple")
        jimple_path = os.path.join("../sootOutput", jimple_file)

        if os.path.exists(jimple_path):

            with open(jimple_path) as f:
                code = f.read()

            st.subheader("Generated Jimple")
            st.code(code, language="java")