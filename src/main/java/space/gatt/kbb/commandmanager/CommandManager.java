package space.gatt.kbb.commandmanager;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import org.reflections.Reflections;
import space.gatt.kbb.annotations.Command;
import space.gatt.kbb.annotations.IMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CommandManager {

	private List<String> commandList = new ArrayList<>();
	private List<Class> listeners = new ArrayList<>();
	private List<Method> listeningMethods = new ArrayList<>();

	private HashMap<String, Class> commandRegistrar = new HashMap<>();
	private HashMap<String, Method> methodRegistrar = new HashMap<>();
	private ArrayList<String> adminusers = new ArrayList<>();


	public void enableSnooper(String dir) {
		Reflections reflections = new Reflections(dir);
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Command.class);
		for (final Class c : classes) {
			Annotation[] annotations = c.getAnnotations();
			for (Annotation a : annotations) {
				if (a instanceof Command) {
					String cmd = ((Command) a).value();
					if (!commandList.contains(cmd)) {
						commandList.add(cmd);
						System.out.println("Registered command " + cmd + " for class " + c.getName());
						Method[] methods = c.getDeclaredMethods();
						for (Method method : methods) {
							if (method.isAnnotationPresent(IMethod.class)) {
								if (Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(
										method.getModifiers())) {
									listeners.add(method.getDeclaringClass());
									listeningMethods.add(method);
									commandRegistrar.put(cmd, method.getDeclaringClass());
									methodRegistrar.put(cmd, method);
									System.out.println("Registered method " + method.getName() + " for command " +
											cmd);
								} else {
									throw new IllegalArgumentException(method.getName() + " in " + c.getSimpleName()
											+ " is not public static!");
								}
							}
						}
					} else {
						System.out.println(
								"The class " + c.getName() + " tried to register the command " + cmd + " a second " +
										"time!");
					}
				}
			}
		}
	}

	public String combineArguments(String[] args) {
		return String.join(" ", args);
	}

	public ArrayList<String> getAdminusers() {
		return adminusers;
	}

	public List<String> getCommandList() {
		return commandList;
	}

	public List<Class> getListeners() {
		return listeners;
	}

	public List<Method> getListeningMethods() {
		return listeningMethods;
	}

	public HashMap<String, Class> getCommandRegistrar() {
		return commandRegistrar;
	}

	public HashMap<String, Method> getMethodRegistrar() {
		return methodRegistrar;
	}

	public boolean hasRoleById(Member user, String id) {
		for (Role r : user.getRoles()) {
			if (r.getId().equalsIgnoreCase(id)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRole(Member user, String roleName, boolean caseInSensitive) {
		if (caseInSensitive) {
			for (Role r : user.getRoles()) {
				if (r.getName().equalsIgnoreCase(roleName)) {
					return true;
				}
			}
			return false;
		} else {
			return hasRole(user, roleName);
		}
	}

	public boolean hasRole(Member user, String roleName) {
		for (Role r : user.getRoles()) {
			if (r.getName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
}
