package engine.assets;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import engine.font.Font;
import engine.graphics.materials.Material;
import engine.graphics.renderer.Renderable3D;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.shader.ShaderUniform;
import engine.graphics.texture.Texture;
import engine.maths.Vec4;

/**
 * Used to manage all the assets used in the game (Textures, fonts and meshes)
 * 
 * @author SabreWulf
 *
 */
public class AssetManager {
	
	private ArrayList<Texture> m_Textures;
	private ArrayList<Font> m_Fonts;
	private ArrayList<Renderable3D[]> m_Meshes;

	/**
	 * Create a new asset manager
	 */
	public AssetManager() {
		m_Textures = new ArrayList<>();
		m_Fonts = new ArrayList<>();
		m_Meshes = new ArrayList<>();
	}

	/**
	 * Get the texture at a given path
	 * 
	 * @param path
	 * @return
	 */
	public Texture getTexture(String path) {
		for (Texture texture : m_Textures) {
			if (texture.getPath().equals(path)) {
				return texture;
			}
		}

		Texture texture = new Texture(path);
		m_Textures.add(texture);
		return texture;
	}

	/**
	 * Get the specified font
	 * 
	 * @param name
	 * @return
	 */
	public Font getFont(String name) {
		for (Font font : m_Fonts) {
			if (font.getName().equals(name)) {
				return font;
			}
		}

		Font font = new Font(name);
		m_Fonts.add(font);
		return font;
	}

	/**
	 * Get a specified model (for 3D)
	 * 
	 * @param model
	 * @param shader
	 * @param texture
	 * @param lit
	 * @return
	 */
	public Renderable3D[] getModel(String model, String shader, String texture, boolean lit) {
		// load a mesh
		for (Renderable3D[] meshes : m_Meshes) {
			if (meshes[0].getFilename().equals(model)) {
				return meshes;
			}
		}

		Texture tex = null;

		if (texture != null) {
			tex = this.getTexture(texture);
		}

		ShaderProgram shaderProgram = new ShaderProgram();
		shaderProgram.loadShader(shader);

		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("modelMatrix"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("viewMatrix"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("projectionMatrix"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("texture_sampler"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("ambientLight"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("material.ambient"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("material.diffuse"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("material.specular"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("material.hasTexture"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("material.reflectance"), 1);
		shaderProgram.getUniformLayout().addShaderUniform(new ShaderUniform("camera_pos"), 1);

		shaderProgram.locateUniforms();

		/* Parse the file */
		AIScene aiScene = Assimp.aiImportFile(model, 0);
		if (aiScene == null) {
			System.out.println("Error loading model: " + model);
		}

		int numMaterials = aiScene.mNumMaterials();
		PointerBuffer aiMaterials = aiScene.mMaterials();
		ArrayList<Material> materials = processMaterials(aiMaterials, numMaterials, tex, shaderProgram);

		int numMeshes = aiScene.mNumMeshes();

		Renderable3D[] meshElements = new Renderable3D[numMeshes];

		PointerBuffer aiMeshes = aiScene.mMeshes();
		for (int i = 0; i < numMeshes; i++) {
			AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
			meshElements[i] = processMesh(aiMesh, materials, shaderProgram, model);
		}

		return meshElements;
	}

	/**
	 * 
	 * @param aiMaterials
	 * @param numMaterials
	 * @param texture
	 * @param shaderProgram
	 * @return
	 */
	private ArrayList<Material> processMaterials(PointerBuffer aiMaterials, int numMaterials, Texture texture,
			ShaderProgram shaderProgram) {
		ArrayList<Material> materials = new ArrayList<>();

		for (int i = 0; i < numMaterials; i++) {
			AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
			AIColor4D colour = AIColor4D.create();

			Vec4 ambient = new Vec4();
			int result = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_AMBIENT,
					Assimp.aiTextureType_NONE, 0, colour);
			if (result == 0) {
				ambient = new Vec4(colour.r(), colour.g(), colour.b(), colour.a());
			}

			Vec4 diffuse = new Vec4();
			result = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_DIFFUSE, Assimp.aiTextureType_NONE, 0,
					colour);
			if (result == 0) {
				diffuse = new Vec4(colour.r(), colour.g(), colour.b(), colour.a());
			}

			Vec4 specular = new Vec4();
			result = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_SPECULAR, Assimp.aiTextureType_NONE,
					0, colour);
			if (result == 0) {
				specular = new Vec4(colour.r(), colour.g(), colour.b(), colour.a());
			}

			Material material = new Material(ambient, diffuse, specular, 1.0f, false);
			material.setTexture(texture);
			materials.add(material);
		}

		return materials;
	}

	/**
	 * 
	 * @param mesh
	 * @param materials
	 * @param shaderProgram
	 * @param filename
	 * @return
	 */
	private Renderable3D processMesh(AIMesh mesh, ArrayList<Material> materials, ShaderProgram shaderProgram,
			String filename) {
		// Vertices
		float[] vertices = new float[mesh.mNumVertices() * 3];

		AIVector3D.Buffer aiVertices = mesh.mVertices();
		int index = 0;
		while (aiVertices.remaining() > 0) {
			AIVector3D aiVertex = aiVertices.get();
			vertices[index + 0] = aiVertex.x();
			vertices[index + 1] = aiVertex.y();
			vertices[index + 2] = aiVertex.z();
			index += 3;
		}

		// UVs
		float[] uvs = new float[mesh.mNumVertices() * 2];

		AIVector3D.Buffer aiUVs = mesh.mTextureCoords(0);

		index = 0;
		while (aiUVs.remaining() > 0) {
			AIVector3D aiUV = aiUVs.get();
			uvs[index + 0] = aiUV.x();
			uvs[index + 1] = aiUV.y();
			index += 2;
		}

		// Normals
		float[] normals = new float[mesh.mNumVertices() * 3];

		AIVector3D.Buffer aiNormals = mesh.mNormals();
		index = 0;
		while (aiNormals.remaining() > 0) {
			AIVector3D aiNormal = aiNormals.get();
			normals[index + 0] = aiNormal.x();
			normals[index + 1] = aiNormal.y();
			normals[index + 2] = aiNormal.z();
			index += 3;
		}

		// indices
		int[] indices = new int[mesh.mNumFaces() * 3];

		index = 0;

		AIFace.Buffer aiIndices = mesh.mFaces();
		while (aiIndices.remaining() > 0) {
			AIFace aiFace = aiIndices.get();

			IntBuffer indexBuffer = aiFace.mIndices();

			int[] faceIndices = new int[3];

			for (int i = 0; i < 3; i++) {
				faceIndices[i] = indexBuffer.get();
			}

			for (int i = 0; i < faceIndices.length; i++) {
				indices[index] = faceIndices[i];
				index++;
			}
		}

		Renderable3D resultMesh = new Renderable3D(vertices, uvs, normals, indices, shaderProgram, filename);
		Material material;
		int materialIdx = mesh.mMaterialIndex();
		if (materialIdx >= 0 && materialIdx < materials.size()) {
			material = materials.get(materialIdx);
		} else {
			material = new Material();
		}

		resultMesh.setMaterial(material);

		return resultMesh;
	}

	/**
	 * Clean up the manager
	 */
	public void cleanup() {
		for (Font font : m_Fonts) {
			font.destroy();
		}
	}
}