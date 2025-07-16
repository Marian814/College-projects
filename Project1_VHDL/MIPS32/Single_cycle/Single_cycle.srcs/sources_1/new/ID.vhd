library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity ID is
Port ( clk : in std_logic;
       instr: in std_logic_vector(25 downto 0);
       en: in std_logic;
       regdst: in std_logic;
       regwr : in std_logic;
       rd1 : out std_logic_vector(31 downto 0);
       rd2 : out std_logic_vector(31 downto 0);
       extop: in std_logic;
       wd: in std_logic_vector(31 downto 0);
       ext_imm: out std_logic_vector(31 downto 0);
       func: out std_logic_vector(5 downto 0);
       sa: out std_logic_vector(4 downto 0)); 
end ID;

architecture Behavioral of ID is

type reg_array is array(0 to 31) of std_logic_vector(31 downto 0);
signal reg_file : reg_array := (others => x"00000000");
signal wa: std_logic_vector(4 downto 0);
signal ra1: std_logic_vector(4 downto 0);
signal ra2: std_logic_vector(4 downto 0);

begin

ra1 <= instr(25 downto 21);
ra2 <= instr(20 downto 16);

process(regdst)
begin
    if regdst = '1' then
        wa <= instr(15 downto 11);
    else 
        wa <= instr(20 downto 16);
    end if;
end process;

process(clk)
begin
    if rising_edge(clk) then
        if en = '1' and regwr = '1' then
            reg_file(conv_integer(wa)) <= wd;
        end if;
    end if;
end process;

func <= instr(5 downto 0);
sa <= instr(10 downto 6);

rd1 <= reg_file(conv_integer(ra1));
rd2 <= reg_file(conv_integer(ra2));

ext_imm(15 downto 0) <= instr(15 downto 0);
ext_imm(31 downto 16) <= (others => instr(15)) when extop = '1' else (others => '0');

end Behavioral;
